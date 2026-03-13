REVISIÓN DE SBSTORE DE PRIMER CURSO

1- Respecto a repositorios:

a. El Hallazgo: Acoplamiento de Persistencia

En el código, la interfaces heredan de los repositorios JPA. Por ejemplo, IUserRepository hereda de JpaRepository<User, Integer>.

Código de ejemplo: package com.example.SPStore.repository; import org.springframework.stereotype.Repository; import com.example.SPStore.model.User; import java.util.Optional; import org.springframework.data.jpa.repository.JpaRepository;

@Repository

public interface IUserRepository extends JpaRepository<User, Integer> { Optional<User> findByEmail(String email);

}

No es solo una definición de "cómo buscar usuarios", sino que está atado a JPA (Java Persistence API).

Por qué es un riesgo: Si mañana quiero cambiar SQL por MongoDB o por una API externa, tendría que cambiar el repositorio que usa el servicio. En Hexagonal, el servicio no debería saber que existe JPA.

b. Argumentos para la transición a Hexagonal

¿Por qué esto está mal?

Actualmente, el repositorio es una implementación técnica, no una definición de negocio. En una arquitectura MVC, esto funciona, pero genera una dependencia circular donde la base de datos dicta cómo se comporta el código. Para pasar a Hexagonal, necesitaremos convertir este IUserRepository en un Puerto (Interface) puro en el dominio y mover la implementación con JPA a la capa de Infraestructura.

2- Respecto a los controller:

Usado de ejemplo, HomeController:

package com.example.SPStore.controller;

import java.util.ArrayList; import java.util.Date; import java.util.List; import java.util.Optional; import java.util.stream.Collectors; import org.slf4j.Logger; import org.slf4j.LoggerFactory; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.stereotype.Controller; import org.springframework.ui.Model; import org.springframework.web.bind.annotation.GetMapping; import org.springframework.web.bind.annotation.PathVariable; import org.springframework.web.bind.annotation.PostMapping; import org.springframework.web.bind.annotation.RequestMapping; import org.springframework.web.bind.annotation.RequestParam; import com.example.SPStore.model.Order; import com.example.SPStore.model.OrderDetails; import com.example.SPStore.model.Product; import com.example.SPStore.model.User; import com.example.SPStore.service.IOrderDetailsService; import com.example.SPStore.service.IOrderService; import com.example.SPStore.service.IUserService; import com.example.SPStore.service.ProductService; import jakarta.servlet.http.HttpSession;

@Controller @RequestMapping("/")

public class HomeController { private final Logger log = LoggerFactory.getLogger(HomeController.class); @Autowired private ProductService productService; @Autowired private IUserService userService; @Autowired private IOrderService orderService; @Autowired private IOrderDetailsService orderDetailsService; List<OrderDetails> details = new ArrayList<OrderDetails>(); Order order = new Order();

@GetMapping("") 
public String home(Model model, HttpSession session) { 
    model.addAttribute("products", productService.findAll()); 
    model.addAttribute("session", session.getAttribute("iduser")); 
    return "user/home"; 
}


@GetMapping("producthome/{id}") 
public String productHome(@PathVariable Integer id, Model model) { 
    Product product = productService.get(id).get(); 
    model.addAttribute("product", product); 
    return "user/producthome"; 
}


@PostMapping("/cart") 
public String addCart(@RequestParam Integer id, @RequestParam Integer amount, Model model) { 
    OrderDetails orderDetails = new OrderDetails(); 
    double sumTotal = 0;
    Optional<Product> optionalProduct = productService.get(id); 
    Product product = optionalProduct.get();



    if (amount > product.getAmount()) { 
        log.warn("Stock insuficiente para producto: {}. Pedido: {}, Disponible: {}", product.getName(), amount, product.getAmount()); 
        return "redirect:/producthome/" + id; 
    }


    orderDetails.setAmount(amount); 
    orderDetails.setPrice(product.getPrice()); 
    orderDetails.setName(product.getName()); 
    orderDetails.setTotal(product.getPrice() * amount); 
    orderDetails.setProduct(product);


    Integer idProduct = product.getId(); 
    boolean isPresent = details.stream().anyMatch(p -> p.getProduct().getId().equals(idProduct));

    if (!isPresent) { 
        details.add(orderDetails); 
    }

    sumTotal = details.stream().mapToDouble(dt -> dt.getTotal()).sum(); 
    order.setTotal(sumTotal); 
    model.addAttribute("cart", details); 
    model.addAttribute("order", order); 
    return "user/cart"; 
}


@GetMapping("/delete/cart/{id}") 
public String deleteProductCart(@PathVariable Integer id, Model model) { 
    List<OrderDetails> updatedOrders = new ArrayList<OrderDetails>(); 
    for (OrderDetails od : details) { 
        if (!od.getProduct().getId().equals(id)) { 
            updatedOrders.add(od); 
        } 
    }


    details = updatedOrders; 
    double sumTotal = details.stream().mapToDouble(dt -> dt.getTotal()).sum(); 
    order.setTotal(sumTotal); 
    model.addAttribute("cart", details); 
    model.addAttribute("order", order); 
    return "redirect:/getCart"; 
}

@GetMapping("/getCart") 
public String getCart(Model model, HttpSession session) { 
    model.addAttribute("cart", details); 
    model.addAttribute("order", order); 
    model.addAttribute("session", session.getAttribute("iduser")); 
    return "/user/cart"; 
}

@GetMapping("/order") 
public String order(Model model, HttpSession session) { 
    User user = userService.findById(Integer.parseInt(session.getAttribute("iduser").toString())).get(); 
    model.addAttribute("cart", details); 
    model.addAttribute("order", order); 
    model.addAttribute("user", user); 
    return "user/purchase"; 
}

@GetMapping("/saveOrder") 
public String saveOrder(HttpSession session) { 
Order newOrder = new Order(); 
newOrder.setDateCreation(new Date()); 
newOrder.setNum(orderService.orderNumberGenerate()); 
newOrder.setTotal(order.getTotal());

User user = userService.findById(Integer.parseInt(session.getAttribute("iduser").toString())).get(); 
newOrder.setUser(user); 
orderService.save(newOrder);

for (OrderDetails dt : details) { 
    OrderDetails newDetail = new OrderDetails(); 
    newDetail.setName(dt.getName()); 
    newDetail.setAmount(dt.getAmount()); 
    newDetail.setPrice(dt.getPrice()); 
    newDetail.setTotal(dt.getTotal()); 
    newDetail.setProduct(dt.getProduct()); 
    newDetail.setOrder(newOrder); 
    orderDetailsService.save(newDetail); 
    Product p = dt.getProduct(); 
    int currentStock = p.getAmount(); 
    int quantityToSubtract = (int) dt.getAmount(); 
    p.setAmount(currentStock - quantityToSubtract); 
    productService.update(p); 
}

details.clear(); 
order = new Order(); 
return "redirect:/success";
}

@GetMapping("/success") 
public String success(Model model, HttpSession session) { 
    model.addAttribute("session", session.getAttribute("iduser")); 
    return "user/success"; 
}

@PostMapping("/search") 
public String searchProduct(@RequestParam String search, Model model) { 
    List<Product> products = productService.findAll().stream() 
            .filter(p -> p.getName().toLowerCase().contains(search.toLowerCase())) 
            .collect(Collectors.toList()); 
    model.addAttribute("products", products); 
    return "user/home"; 
}
}

a. Estado en el Controlador (Stateful Controller)

El hallazgo: Tiuna List<OrderDetails> details y un Order order declarados como variables de instancia en el controlador. Problema (Code Smell): Los controladores en Spring son Singletons por defecto. Esto significa que todos los usuarios comparten el mismo carrito. Si yo añado un producto, tú lo verás en tu carrito. Impacto: Es un error crítico de lógica y seguridad. El estado del carrito debería estar en una base de datos o, como mínimo, gestionado de forma segura en la sesión de cada usuario, pero nunca en el cuerpo del controlador.

b. Lógica de Negocio en el Controlador (Fat Controller)

El hallazgo: El método addCart y saveOrder contienen toda la lógica de cálculo (sumas de totales, validación de stock, creación de detalles). Problema: El controlador está "decidiendo" cosas. Por ejemplo: if (amount > product.getAmount()). Impacto (Arquitectura): Si mañana quiero crear una App móvil, tendrías que copiar y pegar toda esta lógica en un nuevo controlador. La lógica de negocio está "secuestrada" en la capa web. Solución Hexagonal: Esta lógica debería vivir en un Caso de Uso (Aplicación) o en la Entidad de Dominio (Dominio).

c. Acoplamiento Directo con el Modelo (Entity Leaking)

El hallazgo: Uso Product, Order y User directamente. Estas clases, al ser @Entity, están atadas a la base de datos. Problema: Estoy pasando las entidades de la base de datos directamente a la vista (Thymeleaf). Impacto: Si la base de datos cambia, la vista se rompe. Además, en saveOrder, el controlador manipula directamente los setters de las entidades de persistencia.

d. Gestión de Sesión Manual y Frágil

El hallazgo: Integer.parseInt(session.getAttribute("iduser").toString()). Proble de Código: Esto va a lanzar un NullPointerException si el usuario no está logueado. Además, el controlador no debería encargarse de parsear datos de la sesión de esta manera tan cruda.

e. Inyección por @Autowired (Field Injection)

El hallazgo: Uso @Autowired sobre los atributos privados.

Problema: Es un code smell en Spring moderno. Impacto: Dificulta los tests unitarios porque obliga a usar reflexión o a levantar el contexto de Spring. Mejora: Debería usar inyección por constructor.

Argumento para el cambio a Hexagonal:

"El controlador actual sufre de 'exceso de responsabilidad'. No solo gestiona el tráfico web, sino que también ejecuta cálculos financieros y validaciones de stock. Además, el uso de variables de instancia para el carrito de compras representa un riesgo de seguridad y concurrencia crítico en un entorno multiusuario. La transición a Hexagonal permitirá extraer esta lógica a Casos de Uso testeables e independientes del protocolo HTTP."

Respecto a modelo:
El modelo User es el ejemplo perfecto de lo que en arquitectura se llama un Anemic Domain Model (Modelo de Dominio Anémico). Es básicamente una bolsa de datos con anotaciones de base de datos.

a. El Hallazgo: Mezcla de Responsabilidades (Coupling)

El problema: La clase tiene anotaciones de Jakarta Persistence (@Entity, @Table, @Id, @OneToMany). El dominio está casado con JPA. Para probar la lógica de negocio en un test unitario, técnicamente estás cargando dependencias de persistencia. El core del sistema sabe demasiado sobre cómo se guardan los datos en tablas. b. Modelo Anémico (No hay comportamiento)

El problema: El modelo solo tiene getters y setters. No hace nada. Impacto: La lógica que debería ser del usuario (como validar si un email es correcto, cambiar su tipo o encriptar su password) está "desparramada" por los controladores o servicios. Code Smell: Si el User no tiene métodos de negocio, no es un objeto, es una estructura de datos.

c. Exposición total (Setters públicos)

El problema: Hay setters para todo, incluido el id. Impacto: Cualquier parte de la aplicación puede cambiar el id de un usuario o su email sin pasar por ninguna validación. Esto rompe el principio de encapsulamiento. Sugerencia: En un diseño sólido, el id no debería tener setter (se genera solo) y campos críticos deberían cambiarse mediante métodos con nombre de negocio (ej. updateEmail(String newEmail)).

d. Riesgo de Recursividad (Bidireccionalidad)

El problema: Hay @OneToMany hacia Product y Order. Code Smell: Es muy probable que Product también tenga un @ManyToOne hacia User. Impacto: Esto suele causar errores de StackOverflowError si intento imprimir el toString() o serializar a JSON, porque el Usuario llama al Producto, el Producto al Usuario, y así al infinito. Además, cargar un Usuario trae consigo toda su lista de productos y órdenes (problema de rendimiento si no se gestiona el Lazy Loading).

Por qué hacer la mejora:

El modelo actual es una representación directa de la tabla de base de datos, lo que obliga a que toda la lógica de validación resida fuera de la entidad. Al migrar a Hexagonal, separaremos el Domain Model (puro y con lógica) de la Persitence Entity. Esto nos permitirá proteger la integridad de los datos (evitando setters indiscriminados) y facilitar las pruebas unitarias sin necesidad de configurar una base de datos real.
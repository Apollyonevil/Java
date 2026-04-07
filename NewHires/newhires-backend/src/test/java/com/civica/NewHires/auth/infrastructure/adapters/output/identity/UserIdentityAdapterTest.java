package com.civica.newhires.auth.infrastructure.adapters.output.identity;

import com.civica.newhires.submissions.domain.model.Submission;
import com.civica.newhires.submissions.domain.ports.output.SubmissionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserIdentityAdapterTest {

    @Mock
    private SubmissionRepository submissionRepository;

    @InjectMocks
    private UserIdentityAdapter userIdentityAdapter;

    @Test
    void debeEncontrarEmployeeIdPorToken() {
        // Arrange
        String token = "abc-123";
        UUID expectedId = UUID.randomUUID();
        UUID candidateId = UUID.randomUUID();
        UUID employeeId = UUID.randomUUID();
        Submission mockSub = new Submission(candidateId, employeeId, token);
        
        when(submissionRepository.findByToken(token)).thenReturn(Optional.of(mockSub));

        // Act
        Optional<UUID> result = userIdentityAdapter.findEmployeeIdByToken(token);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedId, result.get());
    }

    @Test
    void debeRetornarVacioSiTokenNoExiste() {
        when(submissionRepository.findByToken("fake")).thenReturn(Optional.empty());
        
        Optional<String> result = userIdentityAdapter.findEmailByToken("fake");
        
        assertFalse(result.isPresent());
    }
}
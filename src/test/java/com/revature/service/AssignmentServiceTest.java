package com.revature.service;


import com.revature.exception.ReimbursementAlreadyUpdatedException;
import com.revature.exception.ReimbursementNotFoundException;
import com.revature.model.Reimbursement;
import com.revature.repository.ReimbursementRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
    public class AssignmentServiceTest {

        @Mock
        private ReimbursementRepository reimbursementRepository; // Create a mock object (AssignmentRepository)

        @InjectMocks
        private ReimbursementService as; // Construct an AssignmentService object that utilizes the mock AssignmentRepository by injecting the mock into the object

        // Negative test
        @Test
        public void testGradeAssignmentGradeIsNegative() throws SQLException, ReimbursementAlreadyUpdatedException, ReimbursementNotFoundException {
            // AAA
            // Arrange

            // Act + Assert
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                as.updateReimbursement(1, "hello", 100);
            });
        }

        // Negative test
        @Test
        public void testReimbursementIsNotApprovedOrDenied() throws SQLException {
            // AAA
            // Arrange

            // Mocking a response from assignmentRepository
            when(reimbursementRepository.getReimbursementById(eq(100))).thenReturn(null);

            // Act + Assert
            Assertions.assertThrows(ReimbursementNotFoundException.class, () -> {
                as.updateReimbursement(100, "denied", 2);
            });
        }

        // Negative test
        @Test
        public void testGradeAssignmentExistsButAlreadyGraded() throws SQLException {
            // Arrange
            when(reimbursementRepository.getReimbursementById(eq(1))).thenReturn(new Reimbursement(1, "I need money", 200, "approved", 1, 2));

            // Act + Assert
            Assertions.assertThrows(ReimbursementAlreadyUpdatedException.class, () -> {
                as.updateReimbursement(1, "denied", 10);
            });
        }

        // Positive test
        @Test
        public void testGradeAssignmentPositive() throws SQLException, ReimbursementAlreadyUpdatedException, ReimbursementNotFoundException {
            // Arrange
            when(reimbursementRepository.getReimbursementById(eq(1))).thenReturn(new Reimbursement());

            when(reimbursementRepository.updateReimbursement(eq(1), eq("approved"), eq(150))).thenReturn(true);

            // Act
            boolean actual = as.updateReimbursement(1, "approved", 150);

            // Assert
            Assertions.assertTrue(actual);
        }
}

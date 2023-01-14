package com.isd.authentication;

import com.isd.authentication.commons.Role;
import com.isd.authentication.commons.TransactionStatus;
import com.isd.authentication.commons.TransactionType;
import com.isd.authentication.converter.TransactionConverter;
import com.isd.authentication.domain.Balance;
import com.isd.authentication.domain.Transaction;
import com.isd.authentication.domain.User;
import com.isd.authentication.dto.*;
import com.isd.authentication.service.UserService;
import com.isd.authentication.repository.BalanceRepository;
import com.isd.authentication.repository.TransactionRepository;
import com.isd.authentication.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsersAppTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BalanceRepository balanceRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionConverter transactionConverter;

    @InjectMocks
    private UserService userService;

    @Test
    public void getAll_shouldReturnAllUsers() throws Exception {
        User user1 = new User();
        user1.setId(1);
        user1.setUsername("user1");
        user1.setEnabled(true);

        User user2 = new User();
        user2.setId(2);
        user2.setUsername("user2");
        user2.setEnabled(false);

        List<User> allUsers = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(allUsers);

        Balance balance1 = new Balance();
        balance1.setUserId(1);
        balance1.setCashable(100f);
        balance1.setBonus(50f);

        Balance balance2 = new Balance();
        balance2.setUserId(2);
        balance2.setCashable(200f);
        balance2.setBonus(100f);

        when(balanceRepository.findByUserId(1)).thenReturn(balance1);
        when(balanceRepository.findByUserId(2)).thenReturn(balance2);

        List<UserBalanceDTO> result = userService.getAll();

        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getUserId());
        assertEquals("user1", result.get(0).getUsername());
        assertTrue(result.get(0).getEnabled());
        assertEquals(100, result.get(0).getCashableAmount());
        assertEquals(50, result.get(0).getBonusAmount());

        assertEquals(2, result.get(1).getUserId());
        assertEquals("user2", result.get(1).getUsername());
        assertFalse(result.get(1).getEnabled());
        assertEquals(200, result.get(1).getCashableAmount());
        assertEquals(100, result.get(1).getBonusAmount());
    }

    @Test
    public void createUserEntity_shouldCreateUser() throws Exception {
        UserRegistrationDTO userDto = new UserRegistrationDTO();

        userDto.setUsername("testuser");
        userDto.setPassword("testpassword");
        User newUser = new User();
        newUser.setUsername("testuser");
        newUser.setPassword("testpassword");
        newUser.setEnabled(true);
        newUser.setRole(Role.USER);

        when(userRepository.findByUsername("testuser")).thenReturn(null);
        when(userRepository.save(newUser)).thenReturn(newUser);

        Balance balance = new Balance();
        balance.setUserId(newUser.getId());
        balance.setCashable(0.0f);
        balance.setBonus(0.0f);

        when(balanceRepository.save(balance)).thenReturn(balance);

        User result = userService.createUserEntity(userDto);

        assertEquals("testuser", result.getUsername());
        assertEquals("testpassword", result.getPassword());
        assertEquals(Role.USER, result.getRole());
        assertTrue(result.getEnabled());
    }


    @Test
    public void createUserEntity_shouldThrowException_whenUsernameAlreadyUsed() throws Exception {
        UserRegistrationDTO userDto = new UserRegistrationDTO();
        userDto.setUsername("testuser");
        userDto.setPassword("testpassword");

        User existingUser = new User();
        existingUser.setUsername("testuser");

        when(userRepository.findByUsername("testuser")).thenReturn(java.util.Optional.of(existingUser));
        assertThrows(Exception.class, () -> userService.createUserEntity(userDto), "Username already used");
    }

    @Test
    public void disableOrEnable_shouldDisableUser() throws Exception {
        User user = new User();
        user.setId(1);
        user.setUsername("testuser");
        user.setEnabled(true);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        Balance balance = new Balance();
        balance.setUserId(1);
        balance.setCashable(100f);
        balance.setBonus(50f);

        when(balanceRepository.findByUserId(1)).thenReturn(balance);

        UserBalanceDTO result = userService.disableOrEnable(1, false);

        assertEquals(1, result.getUserId());
        assertEquals("testuser", result.getUsername());
        assertFalse(result.getEnabled());
        assertEquals(100f, result.getCashableAmount());
        assertEquals(50f, result.getBonusAmount());
    }

    @Test
    public void disableOrEnable_shouldThrowException_whenUserNotFound() throws Exception {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> userService.disableOrEnable(1, false), "User not found");
    }


    @Test
    public void findUserById_shouldReturnUserWithTransactions() throws Exception {
        User user = new User();
        user.setId(1);
        user.setUsername("testuser");
        user.setEnabled(true);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        Balance balance = new Balance();
        balance.setUserId(1);
        balance.setCashable(100f);
        balance.setBonus(50f);

        when(balanceRepository.findByUserId(1)).thenReturn(balance);
        Transaction transaction1 = new Transaction();
        transaction1.setId(1);
        transaction1.setUserId(1);
        transaction1.setAmount(20f);
        transaction1.setCategory(TransactionType.DEPOSIT);

        Transaction transaction2 = new Transaction();
        transaction2.setId(2);
        transaction2.setUserId(1);
        transaction2.setAmount(30f);
        transaction2.setCategory(TransactionType.WITHDRAW);

        when(transactionRepository.findAllByUserId(1)).thenReturn(Arrays.asList(transaction1, transaction2));

        TransactionDTO transactionDto1 = new TransactionDTO();
        transactionDto1.setAmount(20f);
        transactionDto1.setType(TransactionType.DEPOSIT);

        TransactionDTO transactionDto2 = new TransactionDTO();
        transactionDto2.setAmount(30f);
        transactionDto2.setType(TransactionType.WITHDRAW);

        when(transactionConverter.convertToDTO(transaction1)).thenReturn(transactionDto1);
        when(transactionConverter.convertToDTO(transaction2)).thenReturn(transactionDto2);

        UserBalanceTransDTO result = userService.findUserById(1);

        assertEquals(1, result.getUserId());
        assertEquals("testuser", result.getUsername());
        assertTrue(result.getEnabled());
        assertEquals(100, result.getCashableAmount());
        assertEquals(50, result.getBonusAmount());
        assertEquals(2, result.getTransactions().size());
        assertEquals(20, result.getTransactions().get(0).getAmount());
        assertEquals(TransactionType.DEPOSIT, result.getTransactions().get(0).getType());
        assertEquals(30, result.getTransactions().get(1).getAmount());
        assertEquals(TransactionType.WITHDRAW, result.getTransactions().get(1).getType());
    }

    @Test
    public void findUserById_shouldThrowException_whenUserNotFound() throws Exception {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> userService.findUserById(1), "User not found");
    }




}

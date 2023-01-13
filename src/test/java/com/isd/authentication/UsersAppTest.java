package com.isd.authentication;

import com.isd.authentication.commons.TransactionStatus;
import com.isd.authentication.domain.Balance;
import com.isd.authentication.domain.Transaction;
import com.isd.authentication.domain.User;
import com.isd.authentication.dto.TransactionDTO;
import com.isd.authentication.dto.TransactionResponseDTO;
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
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsersAppTest {

    @Mock
    UserRepository userRepository;

    @Mock
    BalanceRepository balanceRepository;

    @Mock
    TransactionRepository transactionRepository;

    // TODO: Credo che dovrai eliminare quello di sopra semplice e convertire questo in semplice 'Service', dato che hai già la classe converter. Verifica e procedi con la modifica dopo che il prof da conferma
    @InjectMocks
    UserService userMapperService;

    @Before
    public void setup() {
        // initialize i mock object
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAll() {

        // Configura il comportamento del mock object userRepository
        List<User> users = new ArrayList<>();

        // Crea alcuni utenti di test
        User user1 = new User();
        user1.setId(1);
        user1.setUsername("user1");
        user1.setPassword("password");
        user1.setEnabled(true);
        users.add(user1);
        User user2 = new User();
        user2.setId(2);
        user2.setUsername("user2");
        user2.setPassword("password");
        user2.setEnabled(true);
        users.add(user2);

        when(userRepository.findAll()).thenReturn(users);

        // Chiama il metodo getAll
        Iterable<User> result = userMapperService.getAllEntity();

        // Verifica che il mock object sia stato utilizzato correttamente
        verify(userRepository).findAll();

        // Verifica che gli utenti restituiti siano quelli attesi
        List<User> userList = new ArrayList<>();
        result.forEach(userList::add);
        assertEquals(2, userList.size());
        assertTrue(userList.contains(users.get(0)));
        assertTrue(userList.contains(users.get(1)));
    }

    @Test
    public void testCreate() {
        // Configura il comportamento del mock object userRepository
        User user = new User();
        user.setUsername("newuser");
        user.setPassword("password");
        user.setEnabled(true);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = (User) invocation.getArgument(0);
            savedUser.setId(1);
            return savedUser;
        });

        // Configura il comportamento del mock object balanceRepository
        when(balanceRepository.save(any(Balance.class))).thenAnswer(invocation -> {
            Balance balance = (Balance) invocation.getArgument(0);
            balance.setId(1);
            return balance;
        });

        // Chiama il metodo create
        User createdUser = userMapperService.create(user);

        // Verifica che il mock object sia stato utilizzato correttamente
        verify(userRepository).save(any(User.class));
        verify(balanceRepository).save(any(Balance.class));

        // Verifica che l'utente sia stato creato correttamente
        assertNotNull(createdUser.getId());
        assertEquals("newuser", createdUser.getUsername());

        // Verifica che sia stato creato un oggetto Balance per l'utente appena creato
        ArgumentCaptor<Balance> balanceCaptor = ArgumentCaptor.forClass(Balance.class);
        verify(balanceRepository).save(balanceCaptor.capture());
        Balance balance = balanceCaptor.getValue();
        assertNotNull(balance.getId());
        assertEquals(1, balance.getUserId());
        assertEquals(0.0f, balance.getCashable(), 0.01f);
        assertEquals(0.0f, balance.getBonus(), 0.01f);
    }

    @Test
    public void testRecharge() throws Exception {
        // Configura il comportamento del mock object userRepository
        User user = new User();
        user.setId(1);
        user.setUsername("user1");
        user.setPassword("password");
        user.setEnabled(true);
        when(userRepository.findOneById(1)).thenReturn(user);

        // Configura il comportamento del mock object balanceRepository
        Balance balance = new Balance();
        balance.setUserId(1);
        balance.setCashable(50.0f);
//        when(balanceRepository.findByUserId(1)).thenReturn(Optional.of(balance));
        when(balanceRepository.findByUserId(1)).thenReturn(balance);

        // Crea una richiesta di ricarica di test
        TransactionDTO request = new TransactionDTO();
        request.setUserId(1);
        request.setCircuit("Visa");
        request.setAmount(100.0f);

        // Chiama il metodo recharge
        TransactionResponseDTO response = userMapperService.recharge(request);

        // Verifica che il mock object sia stato utilizzato correttamente
        verify(userRepository).findOneById(1);
        verify(balanceRepository).findByUserId(1);
        verify(transactionRepository).save(any(Transaction.class));
        verify(balanceRepository).save(any(Balance.class));

        // Verifica che la risposta sia quella attesa
        assertEquals("Successful increase", response.getMessage());
        assertEquals(TransactionStatus.CLOSED, response.getStatus());
        assertNotNull(response.getTime());

        // Verifica che il saldo sia stato aggiornato correttamente
        ArgumentCaptor<Balance> balanceCaptor = ArgumentCaptor.forClass(Balance.class);
        verify(balanceRepository).save(balanceCaptor.capture());
        Balance updatedBalance = balanceCaptor.getValue();
        assertEquals(150.0f, updatedBalance.getCashable(), 0.01f);
    }
}

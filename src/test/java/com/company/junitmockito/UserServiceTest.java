package com.company.junitmockito;

import com.company.junitmockito.entity.User;
import com.company.junitmockito.repository.UserRepo;
import com.company.junitmockito.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

//import org.mockito.Mockito;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @MockBean
    UserRepo userRepo;

    @Mock
    UserService userService;

    @Test
    public void getUser_Test() {
        //given

        when(userRepo.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(new User(1l, "Narendra", "1000", new Date())));
        //when
        User user = userService.getUser(2);
        //then
        assertEquals(1, user.getId());
        verify(userRepo).findById(2l);
    }

    @Test
    public void printMessage() throws Exception {
        // given
        doThrow(new Exception("Error Occurred!!")).when(userService).printMessage(true);
        // when
        Executable printMsgExce = () -> userService.printMessage(true);
        // then
        assertThrows(Exception.class, printMsgExce);
    }
}

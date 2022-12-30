package com.isd.authentication.sever;


import com.isd.authentication.dto.UserBalanceDTO;
import com.isd.authentication.mapper.UserMapperService;
import java.util.List;

public class TestServer {
    // local test
    public static void main(String[] args) {
        try {
            // TODO: ritorna un errore quando lo avvio così, perché? devo forse implementare come ha fatto il prof ? Chiedere.
            UserMapperService service1 = new UserMapperService();

            List<UserBalanceDTO> users = service1.getAll();
            System.out.println(users.size());
//            UserServiceR userServiceR = new UserServiceImp();
//            UserDTO myDto = userServiceR.findById(1);

//            UserDTO myDto = userServiceR.getAlbum("First");
//            System.out.println("artist: " + myDto.getArtist());
//            System.out.println("title: " + myDto.getTitle());
//            System.out.println("tracks: " + myDto.getTracks()[0]);
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}

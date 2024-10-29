package data_provider;

import dto.UserDto;
import org.testng.annotations.DataProvider;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DPLogin {

    @DataProvider
    public Iterator<UserDto> addUserFile() {

        List<UserDto> userList = new ArrayList<>();
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader("src/test/resources/login_negative - Sheet1.csv"));
            String line = bufferedReader.readLine();
            while (line != null) {
                String[] splitArray = line.split(",");
                userList.add(UserDto.builder()
                        .username(splitArray[0])
                        .password(splitArray[1])
                        .build());
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return userList.listIterator();
    }
}

package in.gskitchen.indusnet;

import in.gskitchen.indusnet.model.User;
import in.gskitchen.indusnet.repository.CompanyRepository;
import in.gskitchen.indusnet.repository.OtpRepository;
import in.gskitchen.indusnet.repository.SignerRepository;
import in.gskitchen.indusnet.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserResource.class)
public class UserResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private OtpRepository otpRepository;

    @MockBean
    private CompanyRepository companyRepository;

    @MockBean
    private SignerRepository signerRepository;

    User mockUser = new User(9955, "FirstN", "Lastn", "email@mail.com", "companame", "password", Byte.parseByte("1"), new Date());

    //String exampleJson = "{\"id\":\"63\",\"firstName\":\"Fistrrr\",\"lastName\":\"NameLast\",\"email\":\"sabbir.sio@gmail.com\",\"companyName\":\"companame\",\"password\":\"hahlll!123\",\"isVerified\":0,\"createdAt\":\"2019-10-16T09:33:33.000+0000\"}";

    @Test
    public void getUser() throws Exception{
        Optional<User> userOptional = userRepository.findById(Mockito.anyInt());
        Mockito.when(userOptional).thenReturn(Optional.of(mockUser));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/3").with(user("admin").password("password")).accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse());
        String expected = "{id:63, firstName: fname, lastName: lanme, email: email@mail.com, companyName: compName, password: pass, isVerified: 1, createdAt: 2019-10-16T09:33:33.000+0000}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }
}

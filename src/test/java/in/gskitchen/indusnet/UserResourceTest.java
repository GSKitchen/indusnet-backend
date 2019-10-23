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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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

    User mockUser = new User(null, "fname", "lanme", "email@mail.com", "compName", "pasdfdfffssword", (byte) 0, null);

    String exampleJson = "{\"firstName\":\"fname\",\"lastName\":\"lanme\",\"email\":\"email@mail.com\",\"companyName\":\"compName\",\"password\":\"pasdfdfffssword\",\"isVerified\":0}";


    @Test
    public void getUserTest() throws Exception{
        Optional<User> userOptional = userRepository.findById(Mockito.anyInt());
        Mockito.when(userOptional).thenReturn(Optional.of(mockUser));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/3").with(user("admin").password("password")).accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse());
        String expected = "{firstName: fname, lastName: lanme, email: email@mail.com, companyName: compName, password: pasdfdfffssword, isVerified: 0}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void createUserTest() throws Exception{
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(mockUser);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users")
                .with(user("admin").password("password"))
                .accept(MediaType.APPLICATION_JSON)
                .content(exampleJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println("My resp:");
        System.out.println(response.getStatus());

        JSONAssert.assertEquals(String.valueOf(HttpStatus.CREATED.value()), String.valueOf(response.getStatus()), false);
    }
}

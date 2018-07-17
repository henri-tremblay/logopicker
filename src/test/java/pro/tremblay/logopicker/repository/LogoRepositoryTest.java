package pro.tremblay.logopicker.repository;

import pro.tremblay.logopicker.LogopickerApp;
import pro.tremblay.logopicker.domain.Logo;
import pro.tremblay.logopicker.domain.enumeration.Cloud;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Henri Tremblay
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LogopickerApp.class)
public class LogoRepositoryTest {

    @Autowired
    private LogoRepository logoRepository;

    @Test
    public void findByCloud() {
        Logo logo = logoRepository.findByCloud(Cloud.HEROKU);
        assertThat(logo.getCloud()).isEqualTo(Cloud.HEROKU);
        assertThat(logo.getName()).isEqualTo("Heroku");
    }
}

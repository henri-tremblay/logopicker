package pro.tremblay.logopicker.web.rest;

import pro.tremblay.logopicker.LogopickerApp;

import pro.tremblay.logopicker.domain.Logo;
import pro.tremblay.logopicker.repository.LogoRepository;
import pro.tremblay.logopicker.service.LogoService;
import pro.tremblay.logopicker.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static pro.tremblay.logopicker.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import pro.tremblay.logopicker.domain.enumeration.CloudType;
/**
 * Test class for the LogoResource REST controller.
 *
 * @see LogoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LogopickerApp.class)
public class LogoResourceIntTest {

    private static final String DEFAULT_NAME = "Unknown";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final CloudType DEFAULT_CLOUD = CloudType.UNKNOWN;
    private static final CloudType UPDATED_CLOUD = CloudType.LOCALHOST;

    private static final String DEFAULT_URL = "/assets/unknown.png";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    @Autowired
    private LogoRepository logoRepository;

    @Autowired
    private LogoService logoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLogoMockMvc;

    private Logo logo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LogoResource logoResource = new LogoResource(logoService);
        this.restLogoMockMvc = MockMvcBuilders.standaloneSetup(logoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Logo createEntity(EntityManager em) {
        Logo logo = new Logo()
            .name(DEFAULT_NAME)
            .cloud(DEFAULT_CLOUD)
            .url(DEFAULT_URL);
        return logo;
    }

    @Before
    public void initTest() {
        logo = createEntity(em);
    }

    @Test
    @Transactional
    public void createLogo() throws Exception {
        int databaseSizeBeforeCreate = logoRepository.findAll().size();

        // Create the Logo
        restLogoMockMvc.perform(post("/api/logos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(logo)))
            .andExpect(status().isCreated());

        // Validate the Logo in the database
        List<Logo> logoList = logoRepository.findAll();
        assertThat(logoList).hasSize(databaseSizeBeforeCreate + 1);
        Logo testLogo = logoList.get(logoList.size() - 1);
        assertThat(testLogo.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLogo.getCloud()).isEqualTo(DEFAULT_CLOUD);
        assertThat(testLogo.getUrl()).isEqualTo(DEFAULT_URL);
    }

    @Test
    @Transactional
    public void createLogoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = logoRepository.findAll().size();

        // Create the Logo with an existing ID
        logo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLogoMockMvc.perform(post("/api/logos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(logo)))
            .andExpect(status().isBadRequest());

        // Validate the Logo in the database
        List<Logo> logoList = logoRepository.findAll();
        assertThat(logoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLogos() throws Exception {
        // Initialize the database
        logoRepository.saveAndFlush(logo);

        // Get all the logoList
        restLogoMockMvc.perform(get("/api/logos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(logo.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].cloud").value(hasItem(DEFAULT_CLOUD.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())));
    }

    @Test
    @Transactional
    public void getLogo() throws Exception {
        // Initialize the database
        logoRepository.saveAndFlush(logo);

        // Get the logo
        restLogoMockMvc.perform(get("/api/logos/{id}", logo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(logo.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.cloud").value(DEFAULT_CLOUD.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLogo() throws Exception {
        // Get the logo
        restLogoMockMvc.perform(get("/api/logos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLogo() throws Exception {
        // Initialize the database
        logoService.save(logo);

        int databaseSizeBeforeUpdate = logoRepository.findAll().size();

        // Update the logo
        Logo updatedLogo = logoRepository.findOne(logo.getId());
        // Disconnect from session so that the updates on updatedLogo are not directly saved in db
        em.detach(updatedLogo);
        updatedLogo
            .name(UPDATED_NAME)
            .cloud(UPDATED_CLOUD)
            .url(UPDATED_URL);

        restLogoMockMvc.perform(put("/api/logos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLogo)))
            .andExpect(status().isOk());

        // Validate the Logo in the database
        List<Logo> logoList = logoRepository.findAll();
        assertThat(logoList).hasSize(databaseSizeBeforeUpdate);
        Logo testLogo = logoList.get(logoList.size() - 1);
        assertThat(testLogo.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLogo.getCloud()).isEqualTo(UPDATED_CLOUD);
        assertThat(testLogo.getUrl()).isEqualTo(UPDATED_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingLogo() throws Exception {
        int databaseSizeBeforeUpdate = logoRepository.findAll().size();

        // Create the Logo

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLogoMockMvc.perform(put("/api/logos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(logo)))
            .andExpect(status().isCreated());

        // Validate the Logo in the database
        List<Logo> logoList = logoRepository.findAll();
        assertThat(logoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLogo() throws Exception {
        // Initialize the database
        logoService.save(logo);

        int databaseSizeBeforeDelete = logoRepository.findAll().size();

        // Get the logo
        restLogoMockMvc.perform(delete("/api/logos/{id}", logo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Logo> logoList = logoRepository.findAll();
        assertThat(logoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Logo.class);
        Logo logo1 = new Logo();
        logo1.setId(1L);
        Logo logo2 = new Logo();
        logo2.setId(logo1.getId());
        assertThat(logo1).isEqualTo(logo2);
        logo2.setId(2L);
        assertThat(logo1).isNotEqualTo(logo2);
        logo1.setId(null);
        assertThat(logo1).isNotEqualTo(logo2);
    }

    @Test
    @Transactional
    public void getCurrentLogo() throws Exception {
        // Get the logo
        restLogoMockMvc.perform(get("/api/logos/current"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.cloud").value(DEFAULT_CLOUD.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()));
    }
}

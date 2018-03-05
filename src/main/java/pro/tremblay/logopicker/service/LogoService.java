package pro.tremblay.logopicker.service;

import io.github.jhipster.config.JHipsterConstants;
import pro.tremblay.logopicker.domain.Logo;
import pro.tremblay.logopicker.domain.enumeration.CloudType;
import pro.tremblay.logopicker.repository.LogoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Logo.
 */
@Service
@Transactional
public class LogoService {

    private final Logger log = LoggerFactory.getLogger(LogoService.class);

    private final LogoRepository logoRepository;
    private final Environment environment;

    public LogoService(LogoRepository logoRepository, Environment environment) {
        this.logoRepository = logoRepository;
        this.environment = environment;
    }

    /**
     * Save a logo.
     *
     * @param logo the entity to save
     * @return the persisted entity
     */
    public Logo save(Logo logo) {
        log.debug("Request to save Logo : {}", logo);
        return logoRepository.save(logo);
    }

    /**
     * Get all the logos.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Logo> findAll() {
        log.debug("Request to get all Logos");
        return logoRepository.findAll();
    }

    /**
     * Get one logo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Logo findOne(Long id) {
        log.debug("Request to get Logo : {}", id);
        return logoRepository.findOne(id);
    }

    /**
     * Delete the logo by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Logo : {}", id);
        logoRepository.delete(id);
    }

    /**
     * Get current logo.
     *
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Logo findCurrent() {
        log.debug("Request to get current Logo");
        CloudType type = deduceCloudType();
        return logoRepository.findByCloud(type);
    }

    /**
     * Deduce the cloud type from the environment
     *
     * @return cloud type
     */
    public CloudType deduceCloudType() {
        if(environment.acceptsProfiles(JHipsterConstants.SPRING_PROFILE_HEROKU)) {
            return CloudType.HEROKU;
        }
        if(environment.acceptsProfiles(JHipsterConstants.SPRING_PROFILE_CLOUD)) {
            return CloudType.CLOUD_FOUNDRY;
        }
        if(System.getenv("GAE_DEPLOYMENT_ID") != null) {
            return CloudType.GOOGLE;
        }
        if(environment.getProperty("spring.datasource.url").startsWith("jdbc:mysql://localhost:3306")) {
            return CloudType.LOCALHOST;
        }

        return CloudType.UNKNOWN;
    }
}

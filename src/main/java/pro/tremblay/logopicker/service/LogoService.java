package pro.tremblay.logopicker.service;

import pro.tremblay.logopicker.domain.Logo;
import pro.tremblay.logopicker.repository.LogoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public LogoService(LogoRepository logoRepository) {
        this.logoRepository = logoRepository;
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
}

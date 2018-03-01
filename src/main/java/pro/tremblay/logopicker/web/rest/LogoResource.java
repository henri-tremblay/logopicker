package pro.tremblay.logopicker.web.rest;

import com.codahale.metrics.annotation.Timed;
import pro.tremblay.logopicker.domain.Logo;
import pro.tremblay.logopicker.service.LogoService;
import pro.tremblay.logopicker.web.rest.errors.BadRequestAlertException;
import pro.tremblay.logopicker.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Logo.
 */
@RestController
@RequestMapping("/api")
public class LogoResource {

    private final Logger log = LoggerFactory.getLogger(LogoResource.class);

    private static final String ENTITY_NAME = "logo";

    private final LogoService logoService;

    public LogoResource(LogoService logoService) {
        this.logoService = logoService;
    }

    /**
     * POST  /logos : Create a new logo.
     *
     * @param logo the logo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new logo, or with status 400 (Bad Request) if the logo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/logos")
    @Timed
    public ResponseEntity<Logo> createLogo(@RequestBody Logo logo) throws URISyntaxException {
        log.debug("REST request to save Logo : {}", logo);
        if (logo.getId() != null) {
            throw new BadRequestAlertException("A new logo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Logo result = logoService.save(logo);
        return ResponseEntity.created(new URI("/api/logos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /logos : Updates an existing logo.
     *
     * @param logo the logo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated logo,
     * or with status 400 (Bad Request) if the logo is not valid,
     * or with status 500 (Internal Server Error) if the logo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/logos")
    @Timed
    public ResponseEntity<Logo> updateLogo(@RequestBody Logo logo) throws URISyntaxException {
        log.debug("REST request to update Logo : {}", logo);
        if (logo.getId() == null) {
            return createLogo(logo);
        }
        Logo result = logoService.save(logo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, logo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /logos : get all the logos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of logos in body
     */
    @GetMapping("/logos")
    @Timed
    public List<Logo> getAllLogos() {
        log.debug("REST request to get all Logos");
        return logoService.findAll();
        }

    /**
     * GET  /logos/:id : get the "id" logo.
     *
     * @param id the id of the logo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the logo, or with status 404 (Not Found)
     */
    @GetMapping("/logos/{id}")
    @Timed
    public ResponseEntity<Logo> getLogo(@PathVariable Long id) {
        log.debug("REST request to get Logo : {}", id);
        Logo logo = logoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(logo));
    }

    /**
     * DELETE  /logos/:id : delete the "id" logo.
     *
     * @param id the id of the logo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/logos/{id}")
    @Timed
    public ResponseEntity<Void> deleteLogo(@PathVariable Long id) {
        log.debug("REST request to delete Logo : {}", id);
        logoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET  /logos/current : get the current logo.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the logo, or with status 404 (Not Found)
     */
    @GetMapping("/logos/current")
    @Timed
    public ResponseEntity<Logo> getCurrentLogo() {
        log.debug("REST request to get current Logo");
        Logo logo = logoService.findCurrent();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(logo), headers);
    }
}

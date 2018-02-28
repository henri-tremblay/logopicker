package pro.tremblay.logopicker.repository;

import pro.tremblay.logopicker.domain.Logo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Logo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LogoRepository extends JpaRepository<Logo, Long> {

}

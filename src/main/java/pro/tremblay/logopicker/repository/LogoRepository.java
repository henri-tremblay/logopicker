package pro.tremblay.logopicker.repository;

import pro.tremblay.logopicker.domain.Logo;
import pro.tremblay.logopicker.domain.enumeration.Cloud;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Logo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LogoRepository extends JpaRepository<Logo, Long> {

    Logo findByCloud(Cloud cloud);
}

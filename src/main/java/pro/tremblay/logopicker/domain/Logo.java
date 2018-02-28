package pro.tremblay.logopicker.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import pro.tremblay.logopicker.domain.enumeration.CloudType;

/**
 * A Logo.
 */
@Entity
@Table(name = "logo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Logo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "cloud")
    private CloudType cloud;

    @Column(name = "url")
    private String url;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Logo name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CloudType getCloud() {
        return cloud;
    }

    public Logo cloud(CloudType cloud) {
        this.cloud = cloud;
        return this;
    }

    public void setCloud(CloudType cloud) {
        this.cloud = cloud;
    }

    public String getUrl() {
        return url;
    }

    public Logo url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Logo logo = (Logo) o;
        if (logo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), logo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Logo{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", cloud='" + getCloud() + "'" +
            ", url='" + getUrl() + "'" +
            "}";
    }
}

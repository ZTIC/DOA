package pt.ipp.estg.doa.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.ipp.estg.doa.store.model.entity.Earring;

import java.util.List;

public interface EarringRepository extends JpaRepository<Earring, Long> {
    List<Earring> findByClaspType(String claspType);

    List<Earring> findByClaspTypeContainingIgnoreCase(String claspType);
}

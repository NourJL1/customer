package api.uib.customers.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import api.uib.customers.entities.IdentificationDocument;

public interface IdentificationDocumentRepository extends JpaRepository<IdentificationDocument, Long> {
}
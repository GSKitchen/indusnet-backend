package in.gskitchen.indusnet.repository;

import in.gskitchen.indusnet.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
}

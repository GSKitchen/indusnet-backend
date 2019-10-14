package in.gskitchen.indusnet.repository;

import in.gskitchen.indusnet.model.Otp;
import in.gskitchen.indusnet.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRepository extends JpaRepository<Otp, Integer> {
    public Otp findByUser(User user);
}

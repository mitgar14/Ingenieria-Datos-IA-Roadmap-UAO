package com.example.apiDocsTICS.Repository;

import com.example.apiDocsTICS.Model.ContrasenaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.example.apiDocsTICS.Model.UsuarioModel;
import com.example.apiDocsTICS.Model.ENUM.Estado;

public interface IContrasenaRepository extends JpaRepository<ContrasenaModel, Integer> {
    Optional<ContrasenaModel> findByIdUsuarioAndEstado(UsuarioModel idUsuario, Estado estado);

}

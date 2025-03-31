package com.example.apiDocsTICS.Service;
import java.time.LocalDateTime;
import java.util.Optional;

import com.example.apiDocsTICS.Exception.RecursoNoEncontradoException;
import com.example.apiDocsTICS.Model.ContrasenaModel;
import com.example.apiDocsTICS.Model.ENUM.Estado;
import com.example.apiDocsTICS.Model.UsuarioModel;
import com.example.apiDocsTICS.Repository.IContrasenaRepository;
import com.example.apiDocsTICS.Repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class ContrasenaServiceImp implements IContrasenaService{
    @Autowired
    IContrasenaRepository contrasenaRepository;

    @Override
    public String obtenerContrasenaActivaUsuario(Integer idUsuario) {
        Optional<ContrasenaModel> contrasenaOpt = contrasenaRepository.findByIdUsuarioAndEstado(new UsuarioModel(idUsuario), Estado.Activa);
        if (contrasenaOpt.isPresent()) {
            return contrasenaOpt.get().getContrasena();
        } else {
            throw new RecursoNoEncontradoException("No se encontró una contraseña activa para el usuario con id " + idUsuario);
        }
    }
    @Autowired
    IUsuarioRepository usuarioRepository;
    @Override
    public String crearContrasena(ContrasenaModel contrasena){
        Optional<UsuarioModel> usuarioOptional = usuarioRepository.findById(contrasena.getIdUsuario().getIdUsuario());

        if (!usuarioOptional.isPresent()) {
            throw new RecursoNoEncontradoException("Usuario con id " + contrasena.getIdUsuario().getIdUsuario() + " no encontrado");
        }

        // Establecer la fecha actual en el servidor
        contrasena.setFecha(LocalDateTime.now());

        // Guardar la nueva contraseña en la base de datos
        contrasenaRepository.save(contrasena);

        return "La contraseña ha sido creada exitosamente para el usuario con id " + contrasena.getIdUsuario().getIdUsuario();
    }
}

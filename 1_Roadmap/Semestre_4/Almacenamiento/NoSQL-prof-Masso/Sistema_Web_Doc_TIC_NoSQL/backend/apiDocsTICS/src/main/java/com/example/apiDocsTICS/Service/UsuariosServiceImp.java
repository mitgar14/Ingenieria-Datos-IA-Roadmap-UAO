package com.example.apiDocsTICS.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.apiDocsTICS.Exception.RecursoNoEncontradoException;
import com.example.apiDocsTICS.Model.Documents.Contrasenas;
import com.example.apiDocsTICS.Model.ENUM.Estado;
import com.example.apiDocsTICS.Model.UsuariosModel;
import com.example.apiDocsTICS.Repository.IUsuariosRepository;

@Service
public class UsuariosServiceImp implements IUsuariosService {
    @Autowired 
    IUsuariosRepository usuariosRepository;

    @Override
    public String crearUsuario(UsuariosModel usuario) {
        if (usuario.getNombreUsuario() == null || usuario.getNombreUsuario().isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario no puede estar vacío");
        }
        if (usuario.getCorreoUsuario() == null || usuario.getCorreoUsuario().isEmpty()) {
            throw new IllegalArgumentException("El correo de usuario no puede estar vacío");
        }
        if (usuario.getPreguntaSecreta() == null || usuario.getPreguntaSecreta().isEmpty()) {
            throw new IllegalArgumentException("La pregunta secreta no puede estar vacía");
        }
        if (usuario.getResPregunSecret() == null || usuario.getResPregunSecret().isEmpty()) {
            throw new IllegalArgumentException("La respuesta a la pregunta secreta no puede estar vacía");
        }
        if (usuario.getLocalizacion() == null || usuario.getLocalizacion().isEmpty()) {
            throw new IllegalArgumentException("La localización no puede estar vacía");
        }
        for (var localizacion : usuario.getLocalizacion()) {
            if (localizacion.getCiudadOrigen() == null || localizacion.getCiudadOrigen().isEmpty()) {
                throw new IllegalArgumentException("La ciudad de origen no puede estar vacía");
            }
            if (localizacion.getDeptoOrigen() == null || localizacion.getDeptoOrigen().isEmpty()) {
                throw new IllegalArgumentException("El departamento de origen no puede estar vacío");
            }
        }
        if (usuario.getContrasenas() == null || usuario.getContrasenas().isEmpty()) {
            throw new IllegalArgumentException("Las contraseñas no pueden estar vacías");
        }

        // Verificar si el nombre de usuario ya existe
        Optional<UsuariosModel> usuarioExistente = usuariosRepository.findByNombreUsuario(usuario.getNombreUsuario());
        if (usuarioExistente.isPresent()) {
            throw new IllegalArgumentException("El nombre de usuario ya existe");
        }

        // Establecer la fecha actual en las contraseñas
        for (Contrasenas contrasena : usuario.getContrasenas()) {
            contrasena.setFecha(LocalDateTime.now());
        }

        usuariosRepository.save(usuario);
        return "El usuario " + usuario.getNombreUsuario() + " con correo " + usuario.getCorreoUsuario() + " fue creado con éxito";
    }

    @Override
    public UsuariosModel buscarUsuarioPorId(ObjectId usuarioId) {
        Optional<UsuariosModel> usuarioRecuperado = usuariosRepository.findById(usuarioId);
        return usuarioRecuperado.orElseThrow(() -> new RecursoNoEncontradoException("El usuario con el id " + usuarioId + " no existe"));
    }

    @Override
    public List<UsuariosModel> listarUsuarios() {
        return usuariosRepository.findAll();
    }

    @Override
    public String actualizarContrasena(ObjectId usuarioId, UsuariosModel usuario) {
        UsuariosModel usuarioExistente = usuariosRepository.findById(usuarioId).orElse(null);
        if (usuarioExistente != null) {

            // Verificar si la nueva contraseña ya existe
            String nuevaContrasenaValor = usuario.getContrasenas().get(0).getContrasena();
            for (Contrasenas contrasena : usuarioExistente.getContrasenas()) {
                if (contrasena.getContrasena().equals(nuevaContrasenaValor)) {
                    throw new IllegalArgumentException("La nueva contraseña no puede ser igual a una contraseña anterior");
                }
                contrasena.setEstado(Estado.Inactiva);
            }

            Contrasenas nuevaContrasena = new Contrasenas();
            nuevaContrasena.setContrasena(nuevaContrasenaValor);
            nuevaContrasena.setEstado(Estado.Activa);
            nuevaContrasena.setFecha(LocalDateTime.now());
            usuarioExistente.getContrasenas().add(nuevaContrasena);

            usuariosRepository.save(usuarioExistente);
            return "Contraseña actualizada con éxito";
        } else {
            return "Usuario no encontrado";
        }
    }
}
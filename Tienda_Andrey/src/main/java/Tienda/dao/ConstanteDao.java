/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package Tienda.dao;

import Tienda.domain.Constante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConstanteDao 
        extends JpaRepository<Constante,Long> {
    
    public Constante findByAtributo(String stributo);
}


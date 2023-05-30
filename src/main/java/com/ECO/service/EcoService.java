package com.ECO.service;

import com.ECO.model.User;
import com.ECO.repository.EcoRepository;
import com.ECO.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcoService {
    private final EcoRepository ecoRepository;
    @Autowired
    private UserRepository userRepository;
    public EcoService(EcoRepository ecoRepository) {
        this.ecoRepository = ecoRepository;
    }


    public String getHome() {
        return ecoRepository.getHome();
    }
    public String getRegister() {
        return ecoRepository.getRegister();
    }
    public String getLogin() {
        return ecoRepository.getLogin();
    }

    public String getContact() {
        return ecoRepository.getContact();
    }

    public String getAbout() {
        return ecoRepository.getAbout();
    }

    public String getServices() {
        return ecoRepository.getServices();
    }
    public String getAdminSeite() {
        return ecoRepository.getAdminSeite();
    }
    public String getTarifSeite() {
        return ecoRepository.getTarifSeite();
    }
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    public User saveUser(User user){
        return userRepository.save(user);
    }
    public User getUserById(Long id) throws Exception{
        return userRepository.findById(id).orElseThrow(()-> new Exception("User not found with id: "+id));
    }
    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public User createUser(User user){
        return userRepository.save(user);
    }
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
    public void updateUser(User updatedUser)throws Exception{
        User existingUser = userRepository.findById(updatedUser.getId()).orElseThrow(()-> new Exception("User not found with id: "+updatedUser.getId()));
        existingUser.setId(updatedUser.getId());
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setBirth(updatedUser.getBirth());
        existingUser.setAddress(updatedUser.getAddress());
        existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setPaymentMethod(updatedUser.getPaymentMethod());
        existingUser.setTarif(updatedUser.getTarif());
        userRepository.save(existingUser);
    }



}

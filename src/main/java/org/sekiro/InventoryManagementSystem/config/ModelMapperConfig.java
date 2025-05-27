package org.sekiro.InventoryManagementSystem.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    // ModelMapper is a third-party library (from the org.modelmapper package) that automates object-to-object mapping.
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                // Enables direct field-to-field mapping
                // when tru, ModelMapper can map private fields directly(without requiring public getter//setters
                .setFieldMatchingEnabled(true)
                // ModelMapper can read and write private fields(via reflection)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                // Defines how ModelMapper matches properties between src and des obj
                // Matches fields by name and type
                .setMatchingStrategy(MatchingStrategies.STANDARD);
        return modelMapper;
    }
}

package org.example.pokedexapiinterface.configuration;

import org.example.pokedexapiinterface.model.PokemonType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MongoDBConfig {

    @Bean
    public MongoCustomConversions customConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new EnumReadingConverter());
        converters.add(new EnumWritingConverter());
        return new MongoCustomConversions(converters);
    }

    @ReadingConverter
    public static class EnumReadingConverter implements Converter<String, PokemonType> {

        @Override
        public PokemonType convert(final String source) {
            return PokemonType.getType(source);
        }
    }

    @WritingConverter
    public static class EnumWritingConverter implements Converter<PokemonType, String> {
        @Override
        public String convert(final PokemonType pokemonType) {
            return pokemonType.getType();
        }
    }


}

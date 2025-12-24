package ru.music.idgenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class IDGenerator {

    public IDGenerator()
    {

    }

    public String generateID()
    {
        return UUID.randomUUID().toString();
    }

}

package com.nravo.thegame.mobilewars.gamelevel;

/**
 * Contains an array of levels that can be played in the game as well as helper methods
 * to retrieve specific levels
 */
public class Levels {

    public static final LevelDefinition[] AVAILABLE_LEVELS = new LevelDefinition[] {
            new LevelDefinition(1,
            new BuildingDefinition[] {
                new BuildingDefinition(200, 200, Race.ANDROID, BuildingDefinition.BuildingType.SIMPLE),
                new BuildingDefinition(300, 300, Race.APPLE_IOS, BuildingDefinition.BuildingType.TOWER)
            }),
    };

    public static class BuildingDefinition {

        public enum BuildingType {
            SIMPLE,
            BATTERY,
            TOWER
        }

        public final float x;
        public final float y;
        public final Race race; // operating system it belongs to in the beginning
        public final BuildingType buildingType;

        public BuildingDefinition(float x, float y, Race race, BuildingType buildingType) {
            this.x = x;
            this.y = y;
            this.race = race;
            this.buildingType = buildingType;
        }

    }

    /**
     * An object that contains all the information to construct a level
     */
    public static class LevelDefinition {
        public final int levelIndex;
        public final  BuildingDefinition[] buildingsInLevel;

        public LevelDefinition(int levelIndex, BuildingDefinition[] buildings) {
            this.levelIndex = levelIndex;
            this.buildingsInLevel = buildings;
        }
    }

    public enum Race {
        ANDROID,
        APPLE_IOS,
        NEUTRAL
    }
}

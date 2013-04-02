package com.nravo.thegame.mobilewars.gamelevel;

import com.nravo.thegame.mobilewars.managers.ResourceManager;

/**
 * Contains an array of levels that can be played in the game as well as helper methods
 * to retrieve specific levels
 */
public class Levels {

    private static final float CAMERA_WIDTH = ResourceManager.getInstance().cameraWidth;
    private static final float CAMERA_HEIGHT = ResourceManager.getInstance().cameraHeight;

    public static final LevelDefinition[] AVAILABLE_LEVELS = new LevelDefinition[] {
            new LevelDefinition(1,
            new BuildingDefinition[] {
                new BuildingDefinition(CAMERA_WIDTH/4, CAMERA_HEIGHT/4, 10, Race.ANDROID, BuildingDefinition.BuildingType.SIMPLE),
                new BuildingDefinition(CAMERA_WIDTH/4, 3*CAMERA_HEIGHT/4, 10, Race.ANDROID, BuildingDefinition.BuildingType.TOWER),
                new BuildingDefinition(CAMERA_WIDTH/2, 3*CAMERA_HEIGHT/4, 10, Race.ANDROID, BuildingDefinition.BuildingType.TOWER),
                new BuildingDefinition(CAMERA_WIDTH/2, 1*CAMERA_HEIGHT/4, 10, Race.ANDROID, BuildingDefinition.BuildingType.TOWER),
                new BuildingDefinition(CAMERA_WIDTH/2, CAMERA_HEIGHT/2, 10, Race.ANDROID, BuildingDefinition.BuildingType.TOWER),
                new BuildingDefinition(3*CAMERA_WIDTH/4, CAMERA_HEIGHT/4, 10, Race.APPLE_IOS, BuildingDefinition.BuildingType.TOWER),
                new BuildingDefinition(3*CAMERA_WIDTH/4, 3*CAMERA_HEIGHT/4, 10, Race.APPLE_IOS, BuildingDefinition.BuildingType.TOWER)
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
        public final int initialNumberOfUnits;
        public final Race race; // operating system it belongs to in the beginning
        public final BuildingType buildingType;

        public BuildingDefinition(float x, float y, int initialNumberOfUnits, Race race, BuildingType buildingType) {
            this.x = x;
            this.y = y;
            this.initialNumberOfUnits = initialNumberOfUnits;
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

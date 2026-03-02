if [ $# -ne 2 ]; then
    echo "Usage: bash run.sh n (number of locations) m (number of roads)"
    exit 0
fi
javac RoadType.java Location.java City.java Airport.java Road.java
java Main.java $1 $2
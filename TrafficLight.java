class TrafficLight {
    private int greenDuration;
    private int redDuration;
    private int timer = 0;
    private boolean green = true;

    public TrafficLight(int greenDuration, int redDuration) {
        this.greenDuration = greenDuration;
        this.redDuration = redDuration;
    }

    public void update() {
        timer++;
        if (green && timer >= greenDuration) {
            green = false;
            timer = 0;
        } else if (!green && timer >= redDuration) {
            green = true;
            timer = 0;
        }
    }

    public boolean isGreen() {
        return green;
    }

    public void reset() {
        green = true;
        timer = 0;
    }
}

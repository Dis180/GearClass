/**
 * Base class for Gear object.
 *
 * @author Austin Paulick
 */

public class Gear
{

    // Values used by all gears
    // Initiate to '-1' to represent not set
    private int m_numberOfTeeth = -1;
    private double m_pitchDiameter = -1;
    private double m_faceWidth = -1;
    private double m_circularThickness = -1;
    private double m_circularPitch = -1;
    private double m_diametralPitch = -1;
    private float m_rpm = -1;

    // Next gear in the train
    private Gear m_nextGear = null;

    // Constructor with specifying paramters
	public Gear(int teeth, double pitchDiameter, double faceWidth, double circularThickness,
            double circularPitch, double diametralPitch)
    {
        this.m_numberOfTeeth = teeth;
		this.m_pitchDiameter = pitchDiameter;
		this.m_faceWidth = faceWidth;
		this.m_circularThickness = circularThickness;
		this.m_circularPitch = circularPitch;
		this.m_diametralPitch = diametralPitch;
    }

    // Drive the gear from a source
    public void drive(float rpm)
    {
        m_rpm = rpm;
        m_nextGear.drive(m_rpm, m_numberOfTeeth);
    }

    // Drive the gear from a pervious gear and turn next in line if exists
    void drive(float rpm, int teeth)
    {
        // TODO: This is how google told me to calculate it?
        m_rpm = (rpm * teeth) / m_numberOfTeeth;
        if (m_nextGear != null)
        {
            m_nextGear.drive(m_rpm, m_numberOfTeeth);
        }
    }

    @Override
    // dump statistics about the gear
    public String toString()
    {
        String result = String.format("Number of Teeth: [%d] \nPitch Diameter: [%.2f] \nFace Width: [%.2f]\n"
                + "Circular Thickness: [%.2f] \nCircular Pitch: [%.2f] \nDiametral Pitch [%.2f]\n",
                m_numberOfTeeth, m_pitchDiameter, m_faceWidth, m_circularThickness, m_circularPitch,
                m_diametralPitch);

        return result;
    }

////////////////////////////////////////////////////////
//  Getters and Setters Below
////////////////////////////////////////////////////////
    public int getNumberOfTeeth()
    {
        return m_numberOfTeeth;
    }

    public void setNumberOfTeeth(int m_numberOfTeeth)
    {
        this.m_numberOfTeeth = m_numberOfTeeth;
    }

    public double getPitchDiameter()
    {
        return m_pitchDiameter;
    }

    public void setPitchDiameter(double m_pitchDiameter)
    {
        this.m_pitchDiameter = m_pitchDiameter;
    }

    public double getFaceWidth()
    {
        return m_faceWidth;
    }

    public void setFaceWidth(double m_faceWidth)
    {
        this.m_faceWidth = m_faceWidth;
    }

    public double getCircularThickness()
    {
        return m_circularThickness;
    }

    public void setCircularThickness(double m_circularThickness)
    {
        this.m_circularThickness = m_circularThickness;
    }

    public double getCircularPitch()
    {
        return m_circularPitch;
    }

    public void setCircularPitch(double m_circularPitch)
    {
        this.m_circularPitch = m_circularPitch;
    }

    public double getDiametralPitch()
    {
        return m_diametralPitch;
    }

    public void setDiametralPitch(double m_diametralPitch)
    {
        this.m_diametralPitch = m_diametralPitch;
    }

    public double getRpm()
    {
        return m_rpm;
    }

    public void setRpm(float m_rpm)
    {
        this.m_rpm = m_rpm;
    }

    public Gear getNextGear()
    {
        return m_nextGear;
    }

    public void setNextGear(Gear nextGear)
    {
        this.m_nextGear = nextGear;
		
		if( this.m_rpm != -1){
			m_nextGear.drive(m_rpm,m_numberOfTeeth);
		}
    }
}

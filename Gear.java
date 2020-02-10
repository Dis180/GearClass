/**
 * Base class for Gear object. To reduce complexity, only spur gears are considered. All parameter
 * values are based on the standards for coarse pitch mild steel gears.
 *
 * @author Austin Paulick
 * @author Cory Heatwole
 * @author Sam Rennu
 */

public class Gear
{
    // Values used by all gears
    // Initiate to '-1' to represent not set
    private int m_numberOfTeeth = -1;		// Settable (but can only take certain values)
    private int m_diametralPitch = -1;		// Settable (but can only take certain values)
    private double m_pressureAngle = -1;	// Settable (but can only take certain values)
    private double m_pitchDiameter = -1;	// Not settable (from numberOfTeeth and diametralPitch)
    private double m_faceWidth = -1;		// Not settable (from diametralPitch)
    private double m_addendum = -1;		// Not settable (from diametralPitch)
    private double m_dedendum = -1;		// Not settable (from diametralPitch)
    private double m_circularPitch = -1;	// Not settable (from diametralPitch)
    private double m_rpm = -1;			// Not settable (must be driven)

    // Next gear in the train
    private Gear m_nextGear = null;


    // Constructor with specifying paramters
    public Gear(int diametralPitch, int numberOfTeeth, double pressureAngle)
    {
	// User settable parameters
	m_diametralPitch = diametralPitch;
        m_numberOfTeeth = numberOfTeeth;
	m_pressureAngle = pressureAngle;

	// Calculated parameters
	m_pitchDiameter = (double)(m_numberOfTeeth / m_diametralPitch);
	m_faceWidth = 12.0 / m_diametralPitch;
	m_addendum = 1.0 / m_diametralPitch;
	m_dedendum = 1.25 / m_diametralPitch;
	m_circularPitch = Math.PI / m_diametralPitch;
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

    // dump statistics about the gear
    @Override
    public String toString()
    {
        String result = String.format("Diametral Pitch [%d]\n" +
                "Number of Teeth: [%d]\n" +
                "Pressure Angle: [%.2f]\n" +
		"Pitch Diameter: [%.2f]\n" +
		"Face Width: [%.2f]\n" +
		"Addendum: [%.2f]\n" +
		"Dedendum: [%.2f]\n" +
		"Circular Pitch: [%.2f]\n",
                m_diametralPitch, m_numberOfTeeth, m_pressureAngle,  m_pitchDiameter, m_faceWidth,
	        m_addendum, m_dedendum, m_circularPitch);

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

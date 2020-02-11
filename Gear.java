package testgear;

/**
 * Base class for Gear object. To reduce complexity, only spur gears are
 * considered. All parameter values are based on the standards for coarse pitch
 * mild steel gears.
 *
 * @author Austin Paulick
 * @author Cory Heatwole
 * @author Sam Rennu
 */
import java.util.ArrayList;
import java.util.Arrays;

public class Gear
{

    // Values used by all gears
    // Initiate to '-1' to represent not setA
    private int m_numberOfTeeth = -1;		// Settable (but can only take certain values)
    private int m_diametralPitch = -1;		// Settable (but can only take certain values)
    private double m_pressureAngle = -1;	// Settable (but can only take certain values)
    private double m_pitchDiameter = -1;	// Not settable (from numberOfTeeth and diametralPitch)
    private double m_faceWidth = -1;		// Not settable (from diametralPitch)
    private double m_addendum = -1;		// Not settable (from diametralPitch)
    private double m_dedendum = -1;		// Not settable (from diametralPitch)
    private double m_circularPitch = -1;	// Not settable (from diametralPitch)
    private double m_rpm = -1;			// Not settable (must be driven)

    // Valid diametral pitches
    private static final Integer[] m_pitchSet =
    {
        32, 24, 20, 16, 12, 10, 8, 6, 5
    };
    private static final ArrayList<Integer> m_validDiametralPitches = new ArrayList<Integer>(Arrays.asList(m_pitchSet));

    // Valid pressure angles
    private static final Double[] m_angleSet =
    {
        14.5, 20.0, 25.0
    };
    private static final ArrayList<Double> m_validPressureAngles = new ArrayList<Double>(Arrays.asList(m_angleSet));

    // Next gear in the train
    private Gear m_nextGear = null;

    // Constructor with specifying paramters
    public Gear(int diametralPitch, int numberOfTeeth, double pressureAngle)
    {
        // User settable parameters
        setDiametralPitch(diametralPitch);
        setNumberOfTeeth(numberOfTeeth);
        setPressureAngle(pressureAngle);

        // Calculated parameters
        m_pitchDiameter = (double) (m_numberOfTeeth / m_diametralPitch);
        m_faceWidth = 12.0 / m_diametralPitch;
        m_addendum = 1.0 / m_diametralPitch;
        m_dedendum = 1.25 / m_diametralPitch;
        m_circularPitch = Math.PI / m_diametralPitch;
    }

    // Drive the gear from a source
    public void drive(double rpm)
    {
        m_rpm = rpm;
        m_nextGear.drive(m_rpm, m_numberOfTeeth);
    }

    // Drive the gear from a pervious gear and turn next in line if exists
    void drive(double rpm, int teeth)
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
        String result = String.format("Diametral Pitch [%d]\n"
                + "Number of Teeth: [%d]\n"
                + "Pressure Angle: [%.2f]\n"
                + "Pitch Diameter: [%.2f]\n"
                + "Face Width: [%.2f]\n"
                + "Addendum: [%.2f]\n"
                + "Dedendum: [%.2f]\n"
                + "Circular Pitch: [%.2f]\n",
                m_diametralPitch, m_numberOfTeeth, m_pressureAngle, m_pitchDiameter, m_faceWidth,
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
        if (getValidNumbersOfTeeth(m_diametralPitch).contains(m_numberOfTeeth))
        {
            this.m_numberOfTeeth = m_numberOfTeeth;
        }
        else
        {
            System.err.println("Not a valid number of teeth for the given diametral pitch, using a default value of 18.");
            this.m_numberOfTeeth = 18;
        }
    }

    public int getDiametralPitch()
    {
        return m_diametralPitch;
    }

    public void setDiametralPitch(int m_diametralPitch)
    {
        if (m_validDiametralPitches.contains(m_diametralPitch))
        {
            this.m_diametralPitch = m_diametralPitch;
        }
        else
        {
            System.err.println("Not a valid diametral pitch, using a default value of 16.");
            this.m_diametralPitch = 16;
        }
    }

    public double getPressureAngle()
    {
        return m_pressureAngle;
    }

    public void setPressureAngle(double m_pressureAngle)
    {
        if (m_validPressureAngles.contains(m_pressureAngle))
        {
            this.m_pressureAngle = m_pressureAngle;
        }
        else
        {
            System.err.println("Not a valid pressure angle, using a default value of 20.");
            this.m_pressureAngle = 20.0;
        }
    }

    public double getPitchDiameter()
    {
        return m_pitchDiameter;
    }

    public double getFaceWidth()
    {
        return m_faceWidth;
    }

    public double getAddendum()
    {
        return m_addendum;
    }

    public double getDedendum()
    {
        return m_dedendum;
    }

    public double getCircularPitch()
    {
        return m_circularPitch;
    }

    public double getRpm()
    {
        return m_rpm;
    }

    public Gear getNextGear()
    {
        return m_nextGear;
    }

    public void setNextGear(Gear nextGear)
    {
        this.m_nextGear = nextGear;

        if (this.m_rpm != -1)
        {
            m_nextGear.drive(m_rpm, m_numberOfTeeth);
        }
    }

    // Valid numbers of teeth (function instead of variable since values depend on diametral pitch)
    public static ArrayList<Integer> getValidNumbersOfTeeth(int diametralPitch)
    {
        int index = m_validDiametralPitches.indexOf(diametralPitch);
        Integer[] validTeeth;

        switch (index)
        {
            case 0:
                validTeeth = new Integer[]
                {
                    12, 14, 16, 18, 20, 24, 28, 32, 36, 40, 48, 56, 64, 72, 80, 96, 112, 128
                };
                break;
            case 1:

                validTeeth = new Integer[]
                {
                    12, 15, 18, 21, 24, 27, 30, 36, 42, 48, 54, 60, 72, 84, 96, 120, 144
                };
                break;
            case 2:
                validTeeth = new Integer[]
                {
                    12, 14, 15, 16, 18, 20, 24, 25, 30, 35, 40, 45, 50, 60, 70, 80, 84, 90, 100,
                    120, 140, 160, 180, 200
                };
                break;
            case 3:
                validTeeth = new Integer[]
                {
                    12, 14, 15, 16, 18, 20, 24, 28, 30, 32, 36, 40, 48, 56, 60, 64, 72, 80, 96,
                    128, 144, 160, 192
                };
                break;
            case 4:
                validTeeth = new Integer[]
                {
                    12, 13, 14, 15, 16, 18, 20, 21, 24, 28, 30, 36, 42, 48, 54, 60, 66, 72, 84,
                    96, 108, 120, 132, 144, 168, 192, 216
                };
                break;
            case 5:
                validTeeth = new Integer[]
                {
                    12, 14, 15, 16, 18, 20, 24, 25, 28, 30, 35, 40, 45, 48, 50, 55, 60, 70, 80,
                    90, 100, 120, 140, 160, 200
                };
                break;
            case 6:
                validTeeth = new Integer[]
                {
                    12, 14, 15, 16, 18, 20, 22, 24, 28, 32, 36, 40, 44, 48, 56, 60, 64, 72, 80,
                    88, 96, 112, 120, 128
                };
                break;
            case 7:
                validTeeth = new Integer[]
                {
                    12, 14, 15, 16, 18, 21, 24, 27, 30, 33, 36, 42, 48, 54, 60, 66, 72, 84, 96,
                    108, 120
                };
                break;
            case 8:
                validTeeth = new Integer[]
                {
                    12, 14, 15, 16, 18, 20, 24, 25, 28, 30, 35, 40, 45, 50, 60, 70, 80, 100, 110,
                    120, 140, 160, 180
                };
                break;
            default:
                validTeeth = new Integer[]
                {
                    0
                };
        };

        ArrayList<Integer> result = new ArrayList<Integer>(Arrays.asList(validTeeth));
        return result;
    }

    public static ArrayList<Integer> getValidDiametralPitches()
    {
        return m_validDiametralPitches;
    }

    public static ArrayList<Double> getValidPressureAngles()
    {
        return m_validPressureAngles;
    }
}

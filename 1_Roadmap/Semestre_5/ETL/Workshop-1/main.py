import data.raw.export_raw as export_raw
import data.transformation.transformation as transformation
import logging

if __name__ == "__main__":
    export_raw.main()
    transformation.main()

print('Como decía la abuela pachangó, esta vaina por fin nos funcionó.')
logging.info("testing co-authored commits 2")

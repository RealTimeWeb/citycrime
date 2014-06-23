import unittest
from python.src import citycrime


class TestCrimeDatabase(unittest.TestCase):

    def test_get_crime_info(self):
        citycrime.connect()
        citycrime._start_editing()

        keys = ['AggravatedAssault', 'Burglary', 'ForcibleRape', 'LarcenyTheft',
                'Murder', 'Population', 'Property', 'Total', 'VehicleTheft',
                'Violent', 'Year']

        crime_info = citycrime.get_citycrime_information("State==VA")
        citycrime._save_cache()
        self.assertTrue(isinstance(crime_info, dict))


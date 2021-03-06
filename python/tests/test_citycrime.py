import unittest
from python.src import citycrime


class TestCrimeDatabase(unittest.TestCase):


    def test_get_crime_online(self):
        citycrime.connect()
        citycrime._start_editing()

        keys = ['AggravatedAssault', 'Burglary', 'ForcibleRape', 'LarcenyTheft',
                'Murder', 'Population', 'Property', 'Total', 'VehicleTheft',
                'Violent', 'Year']

        crime_info = citycrime.get_crimes("State==VA")
        citycrime._save_cache()
        self.assertTrue(isinstance(crime_info, list))

    # @unittest.skip("Skipping Offline")
    def test_get_crime_offline(self):
        citycrime.disconnect("./cache.json")

        keys = ['AggravatedAssault', 'Burglary', 'ForcibleRape', 'LarcenyTheft',
                'Murder', 'Population', 'Property', 'Total', 'VehicleTheft',
                'Violent', 'Year']

        crime_info = citycrime.get_crimes("State==VA")
        self.assertTrue(isinstance(crime_info, list))
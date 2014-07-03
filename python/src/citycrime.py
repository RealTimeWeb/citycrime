from __future__ import print_function
import sys
import json

HEADER = {'User-Agent': 'RealTimeWeb CityCrime library for educational purposes'}
PYTHON_3 = sys.version_info >= (3, 0)

if PYTHON_3:
    import urllib.error
    import urllib.request as request
    from urllib.parse import quote_plus
else:
    import urllib2
    from urllib import quote_plus

# Auxilary


def _parse_float(value, default=0.0):
    """
    Attempt to cast *value* into a float, returning *default* if it fails.
    """
    if value is None:
        return default
    try:
        return float(value)
    except ValueError:
        return default


def _iteritems(_dict):
    """
    Internal method to factor-out Py2-to-3 differences in dictionary item
    iterator methods

    :param dict _dict: the dictionary to parse
    :returns: the iterable dictionary
    """
    if PYTHON_3:
        return _dict.items()
    else:
        return _dict.iteritems()


def _urlencode(query, params):
    """
    Internal method to combine the url and params into a single url string.

    :param str query: the base url to query
    :param dict params: the parameters to send to the url
    :returns: a *str* of the full url
    """
    return query + '?' + '&'.join(
        key + '=' + quote_plus(str(value)) for key, value in _iteritems(params))


def _get(url):
    """
    Internal method to convert a URL into it's response (a *str*).

    :param str url: the url to request a response from
    :returns: the *str* response
    """
    if PYTHON_3:
        req = request.Request(url, headers=HEADER)
        response = request.urlopen(req)
        return response.read().decode('utf-8')
    else:
        req = urllib2.Request(url, headers=HEADER)
        response = urllib2.urlopen(req)
        return response.read()


def _recursively_convert_unicode_to_str(input):
    """
    Force the given input to only use `str` instead of `bytes` or `unicode`.

    This works even if the input is a dict, list, or a string.

    :params input: The bytes/unicode input
    :returns str: The input converted to a `str`
    """
    if isinstance(input, dict):
        return {_recursively_convert_unicode_to_str(
            key): _recursively_convert_unicode_to_str(value) for key, value in
                input.items()}
    elif isinstance(input, list):
        return [_recursively_convert_unicode_to_str(element) for element in
                input]
    elif not PYTHON_3:
        return input.encode('utf-8')
    elif PYTHON_3 and isinstance(input, str):
        return str(input.encode('ascii', 'replace').decode('ascii'))
    else:
        return input


# Cache

_CACHE = {}
_CACHE_COUNTER = {}
_EDITABLE = False
_CONNECTED = True
_PATTERN = "repeat"


def _start_editing(pattern="repeat"):
    """
    Start adding seen entries to the cache. So, every time that you make a request,
    it will be saved to the cache. You must :ref:`_save_cache` to save the
    newly edited cache to disk, though!
    """
    global _EDITABLE, _PATTERN
    _EDITABLE = True
    _PATTERN = pattern


def _stop_editing():
    """
    Stop adding seen entries to the cache.
    """
    global _EDITABLE
    _EDITABLE = False


def _add_to_cache(key, value):
    """
    Internal method to add a new key-value to the local cache.
    :param str key: The new url to add to the cache
    :param str value: The HTTP response for this key.
    :returns: void
    """
    if key in _CACHE:
        _CACHE[key].append(value)
    else:
        _CACHE[key] = [_PATTERN, value]
        _CACHE_COUNTER[key] = 0


def _clear_key(key):
    """
    Internal method to remove a key from the local cache.
    :param str key: The url to remove from the cache
    """
    if key in _CACHE:
        del _CACHE[key]


def _save_cache(filename="cache.json"):
    """
    Internal method to save the cache in memory to a file, so that it can be used later.

    :param str filename: the location to store this at.
    """
    with open(filename, 'w') as f:
        json.dump({"data": _CACHE, "metadata": ""}, f)


def _lookup(key):
    """
    Internal method that looks up a key in the local cache.

    :param key: Get the value based on the key from the cache.
    :type key: string
    :returns: void
    """
    if key not in _CACHE:
        return ""
    if _CACHE_COUNTER[key] >= len(_CACHE[key][1:]):
        if _CACHE[key][0] == "empty":
            return ""
        elif _CACHE[key][0] == "repeat" and _CACHE[key][1:]:
            return _CACHE[key][-1]
        elif _CACHE[key][0] == "repeat":
            return ""
        else:
            _CACHE_COUNTER[key] = 1
    else:
        _CACHE_COUNTER[key] += 1
    if _CACHE[key]:
        return _CACHE[key][_CACHE_COUNTER[key]]
    else:
        return ""


def connect():
    """
    Connect to the online data source in order to get up-to-date information.

    :returns: void
    """
    global _CONNECTED
    _CONNECTED = True


def disconnect(filename="../src/cache.json"):
    """
    Connect to the local cache, so no internet connection is required.

    :returns: void
    """
    global _CONNECTED, _CACHE
    try:
        with open(filename, 'r') as f:
            _CACHE = _recursively_convert_unicode_to_str(json.load(f))['data']
    except (OSError, IOError) as e:
        raise CityCrimeException(
            "The cache file '{}' was not found.".format(filename))
    for key in _CACHE.keys():
        _CACHE_COUNTER[key] = 0
    _CONNECTED = False


# Exceptions

class CityCrimeException(Exception):
    pass


# Domain Objects


class City(object):
    """
    A City contains
    """

    def __init__(self, agency=None, city=None, state=None, population=None,
                 violent_crime_rate=None, murder_rate=None, rape_rate=None,
                 robbery_rate=None, assault_rate=None, property_crime_rate=None,
                 burglary_rate=None, larceny_rate=None, vehicular_crime_rate=None,
                 year=None):

        """
        Creates a new city object

        :param str agency: The name of the agency for that city
        :param float assault_rate: The assault rate
        :param float burglary_rate: The burglary rate
        :param str city: The name of the city
        :param float larceny_rate: The larceny rate
        :param float murder_rate: The murder rate
        :param int population: The population
        :param float property_crime_rate: The property crime rate
        :param float rape_rate: The rape rate
        :param float robbery_rate: The robbery rate
        :param float state: The name of the state
        :param float vehicular_crime_rate: The vehicular crime rate
        :param float violent_crime_rate: The violent crime rate
        :param int year: The year

        :returns: City
        """

        self.year = year
        self.agency = agency
        self.city = city
        self.state = state
        self.population = population
        self.violent_crime_rate = violent_crime_rate
        self.murder_rate = murder_rate
        self.rape_rate = rape_rate
        self.robbery_rate = robbery_rate
        self.assault_rate = assault_rate
        self.property_crime_rate = property_crime_rate
        self.burglary_rate = burglary_rate
        self.larceny_rate = larceny_rate
        self.vehicular_crime_rate = vehicular_crime_rate

    def __unicode__(self):

        string = """ <City Name: {}> """
        return string.format(self.city)

    def __repr__(self):
        string = self.__unicode__()

        if not PYTHON_3:
            return string.encode('utf-8')

        return string

    def __str__(self):
        string = self.__unicode__()

        if not PYTHON_3:
            return string.encode('utf-8')

        return string

    def _to_dict(self):
        annual_report = dict(year=self.year,
                             city=self.city,
                             agency=self.agency,
                             state=self.state,
                             population=self.population,
                             assault_rate=self.assault_rate,
                             burglary_rate=self.burglary_rate,
                             larceny_rate=self.larceny_rate,
                             murder_rate=self.murder_rate,
                             property_crime_rate=self.property_crime_rate,
                             rape_rate=self.rape_rate,
                             robbery_rate=self.robbery_rate,
                             vehicular_crime_rate=self.vehicular_crime_rate,
                             violent_crime_rate=self.violent_crime_rate)


        return annual_report

    @staticmethod
    def _from_json(json_data):
        """
        Creates a City from json data.

        :param json_data: The raw json data to parse
        :type json_data: dict
        :returns: City
        """

        if json_data is None:
            return City()
        try:
            json_dict = json_data
            city = City(year=json_dict['Year'],
                        city=json_dict['City'],
                        agency=json_dict['Agency'],
                        state=json_dict['State'],
                        population=json_dict['Population'],
                        assault_rate=json_dict['Assault Rate'],
                        burglary_rate=json_dict['Burglary Rate'],
                        larceny_rate=json_dict['Larceny Rate'],
                        murder_rate=json_dict['Murder Rate'],
                        property_crime_rate=json_dict['Property Crime Rate'],
                        rape_rate=json_dict['Rape Rate'],
                        robbery_rate=json_dict['Robbery Rate'],
                        vehicular_crime_rate=json_dict['Vehicular Crime Rate'],
                        violent_crime_rate=json_dict['Violent Crime Rate'])
            return city
        except KeyError:
            raise CityCrimeException("The given information was incomplete.")



# Service Methods


def _fetch_crimes(params):
    """
    Internal method to form and query the server

    :param dict params: the parameters to pass to the server
    :returns: the JSON response object
    """
    baseurl = 'http://think.cs.vt.edu:5000/citycrime'
    query = _urlencode(baseurl, params)

    if PYTHON_3:
        try:
            result = _get(query) if _CONNECTED else _lookup(query)
        except urllib.error.HTTPError:
            raise CityCrimeException("Make sure you entered a valid query")
    else:
        try:
            result = _get(query) if _CONNECTED else _lookup(query)
        except urllib2.HTTPError:
            raise CityCrimeException("Make sure you entered a valid query")

    if not result:
        raise CityCrimeException("There were no results")

    result = result.replace("// ", "")  # Remove Double Slashes
    result = " ".join(result.split())  # Remove Misc 1+ Spaces, Tabs, and New Lines

    try:
        if _CONNECTED and _EDITABLE:
            _add_to_cache(query, result)
        json_res = json.loads(result)
    except ValueError:
        raise CityCrimeException("Internal Error")

    return json_res


def get_crimes(query):
    """
    Forms and poses the query to get information from the database
    :param query: the values to retrieve
    :return: the JSON response
    """
    if not isinstance(query, str):
        raise CityCrimeException("Please enter a valid query")

    params = {'where': query}
    json_res = _fetch_crimes(params)

    info = []
    json_list = json_res['_items']

    for json_dict in json_list:
        report = City._from_json(json_dict)
        info.append(report)

    return [item._to_dict() for item in info]

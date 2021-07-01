#pragma once

#include <string>
#include <vector>

// Removes spaces and tabs from start and end string.
std::string trim(std::string s);

// Splits string at newlines into a vector of string.
std::vector<std::string> splitlines(std::string output);

// Converts a string to lowercase.
std::string toLower(const std::string& str);

// Checks if string s1 contains string s2.
bool contains(const std::string& s1, const std::string& s2);

// Checks if a string can be converted into a valid number.
// This is done by checking that each character in string is a digit.
bool isNumber(const std::string& str);
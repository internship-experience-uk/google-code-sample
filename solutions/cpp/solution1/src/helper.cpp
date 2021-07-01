#include "helper.h"

#include <iostream>
#include <sstream>
#include <vector>

std::string trim(std::string toTrim) {
  size_t trimPos = toTrim.find_first_not_of(" \t");
  toTrim.erase(0, trimPos);
  trimPos = toTrim.find_last_not_of(" \t");
  if (std::string::npos != trimPos) {
    toTrim.erase(trimPos + 1);
  }
  return toTrim;
}

std::vector<std::string> splitlines(std::string output) {
  std::vector<std::string> commandOutput;
  std::stringstream ss(output);
  std::string line;
  while (std::getline(ss, line, '\n')) {
    commandOutput.emplace_back(line);
  }
  return commandOutput;
}

std::string toLower(const std::string& str) {
  std::string output;
  for (const auto& ch : str) {
    output += (char)(tolower(ch));
  }
  return output;
}

bool contains(const std::string& s1, const std::string& s2) {
  std::string s1_lower = toLower(s1);
  std::string s2_lower = toLower(s2);
  return s1_lower.find(s2_lower) != std::string::npos;
}

bool isNumber(const std::string& str) {
  for (const char ch : str) {
    if (!isdigit(ch)) {
      return false;
    }
  }
  return true;
}

#!/bin/env ruby
# coding: utf-8
# -*- ruby -*-

dir = File.dirname(File.dirname(File.expand_path(__FILE__)))
libpath = dir + "/lib"
$:.unshift libpath

require 'pathname'

class Shake
  def initialize args
    pn = Pathname.new args.shift
    pn.each_line do |line|
      line.chomp!
      line.strip!
      next if line.empty?
      line.sub! %r{\d+}, ' '
      # oy. forgot about this:
      line.gsub! "’", "'"
      line.gsub! %r{(\w)(\')(\w)}, '\1666\3'
      line.gsub! %r{[[:punct:]]}, ' '
      line.gsub! '666', "'"
      line.gsub! %r@ {2,}@, ' '
      line.downcase!
      line.split.each do |word|
        puts word
      end
    end
  end
end

Shake.new ARGV

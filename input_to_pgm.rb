#! /usr/bin/env ruby

def new_map(h,w)
  h.times.collect { [nil] * w }
end

def parse(string)
  ls = string.lines.to_a
  tmp = ls.slice!(0)
  tmp = tmp.split
  h = tmp[0].to_i
  w = tmp[1].to_i
  board = new_map(h, w)
  id = 0
  for l in ls
    tmp = l.split.map{|v| v.to_i}
    for row in tmp[0]..tmp[2]
      for col in tmp[1]..tmp[3]
        board[row][col] = id
      end
    end
    id += 1
  end
  [board, id]
end

def scale(map, factor)
  h = map.size
  w = map[0].size
  b = new_map(h*factor, w*factor)
  for r in 0...h
    for c in 0...w
      for i in 0...factor
        for j in 0...factor
          b[r*factor+i][c*factor+j] = map[r][c]
        end
      end
    end
  end
  b
end

def print_ppm(map, block_count)
  print("P3\n", map[0].size, ' ', map.size, "\n", 255, "\n")
  color = {}
  block_count.times {|id| color[id] = [rand(0..255), rand(0..255), rand(0..255)] }
  for row in map
    for cell in row
      c = '255 255 255'
      c = color[cell].join(' ') if cell != nil
      print("\t", c)
    end
    puts
  end
end

if __FILE__ == $0
  map, block_count = parse($stdin.read)
  print_ppm(scale(map, 500 / map.size), block_count)
end

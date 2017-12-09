using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Diagnostics.CodeAnalysis;
using System.Linq;
using NUnit.Framework;

namespace AdventOfCode2017
{
    [TestFixture]
    public class Problem3
    {
        [Test]
        public void Problem2()
        {
            Solve(361527);
        }

        [Test]
        public void Problem1()
        {
            Solve(1024);
            Solve(1);
            Solve(12);
            Solve(23);
            Solve(361527);
        }

        private void Solve(int id)
        {
            var vs = new int[1000, 1000];
            var dxs = new[] { 1, 0, -1, 0 };
            var dys = new[] { 0, -1, 0, 1 };
            var d = 0;
            var s = 1;
            var x = 500;
            var y = 500;
            var n = 1;
            vs[x, y] = 1;
            while (true)
            {
                for (int k = 0; k < 2; k++)
                {
                    for (int i = 0; i < s; i++)
                    {
                        x += dxs[d];
                        y += dys[d];
                        var sum = 0;
                        for(int xx=x-1; xx<=x+1; xx++)
							for (int yy = y - 1; yy <= y + 1; yy++)
								sum += vs[xx, yy];
                        Console.WriteLine(sum);
                        
                        vs[x, y] = sum;
                        
                        if (sum > id)
                        {
                            Console.WriteLine(Math.Abs(x) + Math.Abs(y));
                            return;
                        }
                    }
                    d = (d + 1) % 4;
                }
                s++;
            }
        }
    }
}
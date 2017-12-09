using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Diagnostics.CodeAnalysis;
using System.Linq;
using NUnit.Framework;

namespace AdventOfCode2017
{
    [TestFixture]
    public class Problem4
    {
        [Test]
        public void RunSolve()
        {
            Console.WriteLine(Solve2("aa bb cc aa"));
            Console.WriteLine(Solve2("aa bb cc"));
        }
        public static bool Solve1(string p)
        {
            var words = p.Split();
            return words.Length == words.Distinct().Count();
        }
        public static bool Solve2(string p)
        {
            var words = p.Split();
            return words.Length == words.Select(w => new string(w.OrderBy(a => a).ToArray())).Distinct().Count();
        }
	}
}
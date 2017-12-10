using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using NUnit.Framework;

namespace AdventOfCode2017
{
    [TestFixture]
    public static class Problem
    {
        public static void Solve(int size, int[] lens, int rounds = 1)
        {
            var list = Enumerable.Range(0, size).ToList();
            var cur = 0;
            var skipSize = 0;
            for (var iRound = 0; iRound < rounds; iRound++)
                foreach (var len in lens)
                {
                    var range = list.Skip(cur).Concat(list).Take(len).Reverse().ToList();
                    for (var i = 0; i < len; i++)
                        list[(cur + i) % size] = range[i];
                    cur = (cur + len + skipSize) % size;
                    skipSize++;
                }
            Console.WriteLine(list[0] * list[1]);
            Console.WriteLine(GetHash(list));
        }

        private static string GetHash(List<int> list)
        {
            var bytes = Enumerable.Range(0, 16)
                .Select(start => list.Skip(16 * start).Take(16).Aggregate(0, (a, b) => (byte) a ^ b));
            return string.Join("", bytes.Select(b => b.ToString("X2")));
        }

        private static string input = @"";
        private static string Input => File.ReadAllText(TestContext.CurrentContext.TestDirectory + "\\input.txt");
        private static int[] InputInts => Input.ToInts();

        private static int[][] InputTable => Input.ToIntsTable();

        //static int[] InputLines => File.ReadAllLines(TestContext.CurrentContext.TestDirectory + "\\input.txt").Select(int.Parse).ToArray();
        private static string[] InputLines =>
            File.ReadAllLines(TestContext.CurrentContext.TestDirectory + "\\input.txt");

        public static int[] ToInts(this string text)
        {
            return text.Split(',').Select(int.Parse).ToArray();
        }

        public static int[][] ToIntsTable(this string text)
        {
            return text.ToLines().Select(line => line.Split().Select(int.Parse).ToArray()).ToArray();
        }

        public static string[] ToLines(this string text)
        {
            return text.Split(new[] { "\r", "\n" }, StringSplitOptions.RemoveEmptyEntries);
        }

        [Test]
        public static void RunSolve()
        {
            Solve(5, new[] { 3, 4, 1, 5 });
            Solve(256, InputInts);
            Solve(256, Input.Select(c => (int) c).Concat(new[] { 17, 31, 73, 47, 23 }).ToArray(), 64);
        }
    }
}
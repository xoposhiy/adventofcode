using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using NUnit.Framework;

namespace AdventOfCode2017
{
    [TestFixture]
    public static class Problem9
    {
        public static int Solve1(string input)
        {
            var index = 0;
            var stack = new Stack<char>();
            stack.Push(input[index]);
            var inGarbage = false;
            var d = 0;
            var score = 0;
            while (index < input.Length)
            {
                var c = input[index];
                if (inGarbage)
                {
                    if (c == '!') index++;
                    else if (c == '>') inGarbage = false;
                }
                else
                {
                    if (c == '<')
                    {
                        inGarbage = true;
                    }
                    else if (c == '{')
                    {
                        d++;
                        score += d;
                    }
                    else if (c == '}')
                    {
                        d--;
                    }
                }
                index++;
            }
            return score;
        }
        public static int Solve2(string input)
        {
            var index = 0;
            var stack = new Stack<char>();
            stack.Push(input[index]);
            var inGarbage = false;
            var d = 0;
            var g = 0;
            var score = 0;
            while (index < input.Length)
            {
                var c = input[index];
                if (inGarbage)
                {
                    if (c == '!') index++;
                    else if (c == '>') inGarbage = false;
                    else g++;
                }
                else
                {
                    if (c == '<')
                    {
                        inGarbage = true;
                    }
                    else if (c == '{')
                    {
                        d++;
                        score += d;
                    }
                    else if (c == '}')
                    {
                        d--;
                    }
                }
                index++;
            }
            return g;
        }

        private static string Input => File.ReadAllText(TestContext.CurrentContext.TestDirectory + "\\input.txt");
        private static int[] InputInts => Input.ToInts();

        private static int[]
            [] InputTable => Input.ToIntsTable();

        private static string[] InputLines =>
            File.ReadAllLines(TestContext.CurrentContext.TestDirectory + "\\input.txt");

        public static int[] ToInts(this string text)
        {
            return text.Split().Select(int.Parse).ToArray();
        }

        public static int[]
            [] ToIntsTable(this string text)
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
            Assert.AreEqual(1, Solve1("{}"));
            Assert.AreEqual(3, Solve1("{{}}"));
            Assert.AreEqual(6, Solve1("{{{}}}"));
            Assert.AreEqual(16, Solve1("{{{},{},{{}}}}"));
            Assert.AreEqual(1, Solve1("{<a>,<a>,<a>,<a>}"));
            Assert.AreEqual(9, Solve1("{{<ab>},{<ab>},{<ab>},{<ab>}}"));
            Assert.AreEqual(9, Solve1("{{<!!>},{<!!>},{<!!>},{<!!>}}"));
            Assert.AreEqual(3, Solve1("{{<a!>},{<a!>},{<a!>},{<ab>}}"));
            Assert.AreEqual(5, Solve1("{{},{}}"));
            Assert.AreEqual(1, Solve1("{<{},{},{{}}>}"));
            Assert.AreEqual(9, Solve1("{{<a>},{<a>},{<a>},{<a>}}"));
            Assert.AreEqual(3, Solve1("{{<!>},{<!>},{<!>},{<a>}}"));
            Assert.AreEqual(10, Solve2("<{o\"i!a,<{i<a>"));
            Console.WriteLine(Solve1(Input));
            Console.WriteLine(Solve2(Input));
        }
    }
}
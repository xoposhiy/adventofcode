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
        public static void Solve(params string[] lines)
        {
            var edges = lines.Select(line => line.Split('/').Select(int.Parse).ToArray()).ToArray();
            List<int> ans = null;
            Bfs(edges, new HashSet<int>(Enumerable.Range(0, edges.Length)), new List<int> { 0 }, ref ans);
        }

        private static void Bfs(int[][] edges, HashSet<int> free, List<int> cur, ref List<int> ans)
        {
            var port = cur.Last();
            var found = false;
            foreach (var partIndex in free.ToList())
            {
                var endIndex = edges[partIndex].IndexOf(port);
                if (endIndex >= 0)
                {
                    free.Remove(partIndex);
                    cur.Add(edges[partIndex][1 - endIndex]);

                    Bfs(edges, free, cur, ref ans);

                    free.Add(partIndex);
                    cur.RemoveAt(cur.Count - 1);
                    found = true;
                }
            }
            if (found) return;
            if (ans == null || 
                cur.Count > ans.Count ||
                cur.Count == ans.Count && 2 * cur.Sum() - cur.Last() > 2 * ans.Sum() - ans.Last())
            {
                ans = cur.ToList();
                Console.WriteLine($"len {ans.Count}, strength {(2 * ans.Sum() - ans.Last())}, bridge: {ans.StrJoin(" ")}");
            }
        }

        private static string[] InputLines =>
            File.ReadAllLines(TestContext.CurrentContext.TestDirectory + "\\input.txt");

        [Test]
        public static void RunSolve()
        {
            Solve(InputLines);
        }
    }
}
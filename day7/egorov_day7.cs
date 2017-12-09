using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text.RegularExpressions;
using NUnit.Framework;

namespace AdventOfCode2017
{
    class Node
    {
        public string Name;
        public int W;
        public int FullW;
        public List<Node> Items;

        public static Node Create(string name, Dictionary<string, Tuple<string, int, string[]>> nodes)
        {
            var children = nodes[name].Item3.Select(n => Create(n, nodes)).ToList();
            var weights = children.OrderBy(c => c.FullW).ToArray();
            var root = new Node()
            {
                Name = name,
                W = nodes[name].Item2,
                FullW = nodes[name].Item2 + children.Sum(s => s.FullW),
                Items = children,
                IsBalanced = children.Count == 0 || weights.First().FullW == weights.Last().FullW
                
            };
            children.ForEach(c => c.Parent = root);
            return root;
        }

        public override string ToString()
        {
            return ToString2("");
        }

        private string ToString2(string ident)
        {
            var me = ident + $"{nameof(Name)}: {Name}, {nameof(W)}: {W}, {nameof(FullW)}: {FullW}, {nameof(IsBalanced)}: {IsBalanced}";
            var items = string.Join("\n", Items.Select(i => i.ToString2(ident + "  ")));
            return me + "\n" + items;
        }

        public bool IsBalanced;

        public Node Parent;

        
        public Node GetEdgeUnbalanced()
        {
            //Console.WriteLine(this);
            var unbNodes = Items.Where(n => !n.IsBalanced).ToList();
            var node = unbNodes.SingleOrDefault();
            if (node == null) return this;
            return node.GetEdgeUnbalanced();
        }
    }
    [TestFixture]
    public static class Problem7
    {
        [Test]
        public static void RunSolve()
        {
            Console.WriteLine("root=" + Solve1(InputLines));
            Solve2(InputLines);
        }
        public static string Solve1(string[] lines)
        {
            
            var nodes = lines.Select(ParseLine).ToDictionary(n => n.Item1, n => n);
            return nodes.Keys.FirstOrDefault(n => !nodes.Values.Any(p => p.Item3.Contains(n)));

        }
        
        public static void Solve2(string[] lines)
        {
            var linesByName = lines.Select(ParseLine).ToDictionary(n => n.Item1, n => n);
            var root = linesByName.Keys.First(n => !linesByName.Values.Any(p => p.Item3.Contains(n)));
            var tree = Node.Create(root, linesByName);
            var node = tree.GetEdgeUnbalanced();
            foreach (var item in node.Items)
            {
                Console.WriteLine(item.FullW + " " + item.W);
            }
            Console.WriteLine("Дальше вручную, чувак!");
        }


        private static Tuple<string, int, string[]> ParseLine(string arg)
        {
            var match = Regex.Match(arg, @"(.+)\s(.+)\)(\s->\s(.+))?");
            var tuple = Tuple.Create(
                match.Groups[1].Value,
                int.Parse(match.Groups[2].Value.Trim('(', ')')),
                match.Groups[4].Value.Split(',').Select(s => s.Trim()).Where(s => s != "").ToArray()
            );
            return tuple;
        }

        private static string input = @"";
        static string Input => File.ReadAllText(TestContext.CurrentContext.TestDirectory + "\\input.txt");
        static int[] InputInts => Input.ToInts();
        static int[][] InputTable => Input.ToIntsTable();
        //static int[] InputLines => File.ReadAllLines(TestContext.CurrentContext.TestDirectory + "\\input.txt").Select(int.Parse).ToArray();
        static string[] InputLines => File.ReadAllLines(TestContext.CurrentContext.TestDirectory + "\\input.txt");

        public static int[] ToInts(this string text)
        {
            return text.Split().Select(int.Parse).ToArray();
        }
        public static int[][] ToIntsTable(this string text)
        {
            return text.ToLines().Select(line => line.Split().Select(int.Parse).ToArray()).ToArray();
        }
        public static string[] ToLines(this string text)
        {
            return text.Split(new[] { "\r", "\n" }, StringSplitOptions.RemoveEmptyEntries);
        }
    }
}
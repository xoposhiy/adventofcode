using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text.RegularExpressions;
using NUnit.Framework;

namespace AdventOfCode2017
{
    [TestFixture]
    public static class Problem8
    {
        [Test]
        public static void RunSolve()
        {
            Console.WriteLine(
                "solve1 = " + 
                Solve1(InputLines.Select(l => new Instr(l)).ToList()));
        }

        public static object Solve1(List<Instr> cmds)
        {
            var regs = new Dictionary<string, int>();
            cmds.ForEach(c => c.Execute(regs));
            //return regs.Values.Max();
            return regs["__max"];
        }
        
        public class Instr
        {
            public Instr(string s)
            {
                var ps = s.Split();
                Reg = ps[0];
                Op = ps[1];
                Arg = int.Parse(ps[2]);
                IfReg = ps[4];
                IfOp = ps[5];
                IfArg = int.Parse(ps[6]);
            }

            public string Reg;
            public string Op;
            public int Arg;
            public string IfReg;
            public string IfOp;
            public int IfArg;

            public void Execute(Dictionary<string, int> regs)
            {
                if (Apply(IfOp, regs.GetOrDefault(IfReg), IfArg))
                {
                    regs[Reg] = Execute(Op, regs.GetOrDefault(Reg), Arg);
                    regs["__max"] = Math.Max(regs.GetOrDefault("__max"), regs[Reg]);
                }
            }

            private bool Apply(string op, int reg, int arg)
            {
                if (op == ">=") return reg >= arg;
                if (op == ">") return reg > arg;
                if (op == "<") return reg < arg;
                if (op == "<=") return reg <= arg;
                if (op == "!=") return reg != arg;
                if (op == "==") return reg == arg;
                throw new Exception(op);
            }

            private int Execute(string op, int reg, int arg)
            {
                if (op == "inc") return reg + arg;
                if (op == "dec") return reg - arg;
                throw new Exception(op);
            }
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
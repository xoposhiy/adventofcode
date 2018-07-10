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
        [Test]
        public static void RunSolve()
        {
            Solve();
        }

        public static void Solve()
        {
            var A = 0;
            var B = 1;
            var C = 2;
            var D = 3;
            var E = 4;
            var F = 5;
            var state=A;
            var tape = new Dictionary<int, int>();
            var cursor = 0;

            Action<int, int, int> Do = (write, move, st) => {
                tape[cursor] = write;
                cursor += move;
                state = st;
            };

            for(int i=0; i<12481997; i++){
                // Perform a diagnostic checksum after 12481997 steps.
                var sym = tape.GetOrDefault(cursor);
                if (state == A){
                    if (sym == 0) 
                        Do(1, 1, B);
                    else
                        Do(0, -1, C);
                }
                else if (state == B){
                    if (sym == 0) 
                        Do(1, -1, A);
                    else
                        Do(1, 1, D);
                }
                else if (state == C){
                    if (sym == 0) 
                        Do(0, -1, B);
                    else
                        Do(0, -1, E);
                }
                else if (state == D){
                    if (sym == 0) 
                        Do(1, 1, A);
                    else
                        Do(0, 1, B);
                }
                else if (state == E){
                    if (sym == 0) 
                        Do(1, -1, F);
                    else
                        Do(1, -1, C);
                }
                else if (state == F){
                    if (sym == 0) 
                        Do(1, 1, D);
                    else
                        Do(1, 1, A);
                }
            }
            Console.WriteLine(tape.Values.Count(v => v == 1));
        }

        private static string[] InputLines =>
            File.ReadAllLines(TestContext.CurrentContext.TestDirectory + "\\input.txt");
    }
}
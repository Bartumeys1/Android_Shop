﻿using Sim23.Data.Entitys.Identity;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Sim23.Data.Entitys
{
    [Table("tblCategories")]
    public class CategoryEntity : BaseEntity<int>
    {
        [Required , StringLength(255)]
        public string Name { get; set; }
        public int Priority { get; set; }
        [StringLength(255)]
        public string Image { get; set; }
        [StringLength(4000)]
        public string Description { get; set; }
        [ForeignKey("User")]
        public int? UserId { get; set; }
        public UserEntity User { get; set; }
    }
}

using AutoMapper;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Sim23.Abstract;
using Sim23.Data;
using Sim23.Data.Entitys.Identity;
using Sim23.Models;

namespace Sim23.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class UserController : ControllerBase
    {
        private readonly AppEFContext _userManager;
        private readonly IJwtTokenService _jwtTokenService;
        private readonly IMapper _mapper;
        public UserController(AppEFContext userManager, IJwtTokenService jwtTokenService, IMapper mapper)
        {
            _userManager = userManager;
            _jwtTokenService = jwtTokenService;
            _mapper = mapper;
        }

        [HttpGet("{email}")]
        public async Task<IActionResult> GetByEmail(string email)
        {
            var user = await _userManager.Users.Include(u => u.UserRoles).ThenInclude(ur => ur.Role).ToListAsync();
           
            if (user == null) return BadRequest("User not fount");

            return Ok(user);   
            return Ok(_mapper.Map<UserViewModel>(user));
         }
    }
}
